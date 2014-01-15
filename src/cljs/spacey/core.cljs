(ns spacey.core
  (:require
    [spacey.repl :as repl]
    [spacey.problems :as problems]
    [spacey.renderer :refer [render]]
    [spacey.events :refer [listen]]
    [cljs.core.async :refer [<! >! chan alts! put!]])
  (:require-macros
    [cljs.core.async.macros :refer [go]])
  (:use
    [spacey.util :only [log]])
  (:use-macros
    [dommy.macros :only [sel1]]))

(defn constructor []
  {:editor (atom nil)
   ;; todo: make atom to mutate with subject
   :editor-options {:autofocus true
                    :matchBrackets true}
   :subject (atom nil)
   :problem (atom nil)})

(defn ask-next [state]
  (reset! (:problem state) (problems/get-next (:subject state)))
  (render :problem state))

(defn add-editor-listeners [ch k r old-editor editor]
  (when-not (nil? editor)
    (.on editor "change" #(put! ch :changed))))

(defn start-session [state]
  (let [eval-in-chan (chan)
        eval-out-chan (chan)
        editor-chan (chan)]
    (repl/start state eval-in-chan eval-out-chan)
    (add-watch (:editor state) nil (partial add-editor-listeners editor-chan))

    ;; todo: pick a subject - currently fixed as :clojure
    (reset! (:subject state) :clojure)
    (ask-next state)

    (go (while true
          (let [[event ch] (alts! [editor-chan eval-out-chan])]
            (log event)
            (condp = ch
              editor-chan (condp = event
                            :changed (>! eval-in-chan :update)
                            :done (>! eval-in-chan :evaluate))
              eval-out-chan (;; check that result is correct first
                             ask-next state)))))))

(defn init []
  (let [state (constructor)
        clicks-chan (listen (sel1 :#start) :click)]
    (go (while true
          (.log js/console (<! clicks-chan))
          (start-session state)))))

(.ready (js/jQuery js/document) init)
