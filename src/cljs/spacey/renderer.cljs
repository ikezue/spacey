(ns spacey.renderer
  (:require
    [spacey.codemirror :as codemirror]
    [spacey.templates :as templates]
    [spacey.events :refer [listen]]
    [jayq.core :as $]
    [dommy.core :as dommy]
    [dommy.utils :as utils]
    [cljs.core.async :refer [put! chan <!]])
  (:require-macros
    [cljs.core.async.macros :refer [go]])
  (:use
    [spacey.util :only [log wait]]
    [jayq.core :only [$]])
  (:use-macros
    [dommy.macros :only [node sel sel1]]))

(def fadeout-time 300)
(def enter-key 13)

(defn remove-previous-problem []
  (let [$el ($ :.problem-box)]
    ($/fade-out $el
      #($/remove $el))))

(defn remove-static-content []
  ($/fade-out ($ :.static)))

(defn format-code []
  (let [$area (sel1 :textarea)
        $shadow (sel1 :.code-area-shadow)
        $container (sel1 :.code-area)]
    (log "formatting")
    (dommy/set-text! $shadow (dommy/value $area))
    (dommy/add-class! $container "active")))

(defn format-code-input []
  (log "starting code formatting..."))

(defn render-problem [state]
  (remove-previous-problem)
  (remove-static-content)
  (let [$elem ($ (templates/problem (:problem state)))]
    (wait fadeout-time (fn []
                         ($/prepend ($ :.container) $elem)
                         ($/fade-in $elem)
                         (reset! (:editor state)
                                 (codemirror/editor (sel1 :.code-area) (:editor-options state)))
                         (format-code-input)))))

(defn render [template state]
  (condp template
    :problem (render-problem state)))
