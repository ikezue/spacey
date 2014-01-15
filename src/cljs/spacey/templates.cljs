(ns spacey.templates
  (:use-macros [dommy.macros :only [deftemplate node sel sel1]]))

(deftemplate problem [problem]
  [:div {:class "problem-box"}
    [:div {:class "problem"}
      [:h3 {:class "question"} (:question problem)]
      [:div {:class "answer"}
        [:div {:class "code-box"}
          [:form
            [:textarea {:class "code-area" :name "code-area"} ]]]]]])
