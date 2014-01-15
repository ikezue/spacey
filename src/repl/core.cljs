(ns repl
  (:require [cljs.repl :as repl]))

(defn evaluate [text & [error-fn]]
  "Evaluates ClojureScript code entered in the browser using the Cljs-in-Cljs
   REPL [https://github.com/kanaka/clojurescript]. Allows replacing the error
   function with an optional argument."
  (if error-fn
    (set! *err* #(error-fn %)))
  (repl/eval-print text))

(defn init [print-fn return-fn error-fn]
  "Initializes the Cljs-in-Cljs REPL to use print-fn, return-fn and error-fn as
   output stream (for `print`, `println` etc), for displaying results of
   evaluated expressions and for errors, respectively."
  (set! *out* #(print-fn % "out"))
  (set! *rtn* #(return-fn %))
  (set! *err* #(error-fn %))
  (set! *print-fn* #(*out* %1))
  (repl/init))
