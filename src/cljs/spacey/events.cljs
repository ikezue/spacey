(ns spacey.events
  (:require
    [dommy.core :as dommy]
    [cljs.core.async :refer [put! chan]]))

(defn listen [el type & {:keys [pred]}]
  (let [ch (chan)]
    (dommy/listen! el type
                   (if pred
                     (fn [e] (when (pred e) (put! ch e)))
                     (fn [e] (put! ch e))))
    ch))
