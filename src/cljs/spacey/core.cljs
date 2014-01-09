(ns spacey.core)

(defn ^:export main []
  (.write js/document "<p>Welcome to the Spacey app!</p>"))

(defn init []
  (-> js/document .-body (.setAttribute "style" "background:green")))

(.ready (js/jQuery js/document) init)

(-> js/document .-body (.setAttribute "style" "background:orange"))
