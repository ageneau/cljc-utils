(ns ageneau.utils.macros
  (:require [clojure.java.io :as io]))

;; https://clojureverse.org/t/best-practices-for-importing-raw-text-files-into-clojurescript-projects/2569
(defmacro inline-resource [resource-path]
  (slurp (clojure.java.io/resource resource-path)))

(defmacro handler-fn
  ([& body]
   `(fn [~'event] ~@body nil)))

(defmacro fn-traced
  "Stub fn-traced for clojure"
  {:arglists '[(fn name? [params*] exprs*) (fn name? ([params*] exprs*) +)]}
  [& definition]
  `(fn ~@definition))
