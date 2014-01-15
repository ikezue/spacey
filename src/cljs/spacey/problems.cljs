(ns spacey.problems)

;; A Problem consists of a question (a string), an expected result, and a
;; vector of correct answers. For questions with multiple answers, the first
;; answer should be the expected, idiomatic answer. An answer is a map that
;; consists of tags (function names in the answer) and other metadata. This
;; could be used to prompt the user for other possible answers to the question.
(defrecord Problem [question result answers])

(def problems
  {:clojure
   [(Problem. "Square all numbers in the vector [1 2 3 4 5]."
              "[1 4 9 16 25]"
              [{:answer '(map #((* % %)) [1 2 3 4 5])
                :keywords ["map"]}])]})

(defn get-next [subject]
  "Returns the next problem in the given subject."
  (rand-nth (problems subject)))
