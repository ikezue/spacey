(ns spacey.codemirror
  (:use [spacey.util :only [map->js]]))

(defn editor [textarea options]
  (.fromTextArea js/CodeMirror textarea, (map->js options)))

(defn get-value
  ([editor] (.getValue editor))
  ([editor separator] (.getValue editor separator)))
