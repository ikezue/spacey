(ns spacey.repl
  (:require [cljs.core.async :refer [! chan alts!]]
            [spacey.codemirror :as codemirror])
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:use [spacey.util :only [log]]))

(def return-chan (chan))
(def error-chan (chan))

(defn print-fn [text]
  (log text))

(defn evaluate [state & {:keys [show-errors]}]
  (let [editor (deref (:editor state))
        input (codemirror/get-value editor)
        error-fn (if show-errors #(go (>! error-chan %)) #())]
    (.evaluate js/repl input error-fn)))

(defn start [state in-chan out-chan]
  (let [return-fn #(go (>! return-chan %))
        error-fn #()]
    (.init js/repl print-fn return-fn error-fn)
    (go (while true
          (let [[v ch] (alts! [in-chan return-chan error-chan])]
            (condp = ch
              return-chan (log v)
              error-chan (log v)
              in-chan (condp = v
                        :update (evaluate state :show-errors false)
                        :evaluate (evaluate state :show-errors true))))))))
