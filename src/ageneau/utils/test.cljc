(ns ageneau.utils.test
  (:require  #?(:clj [clojure.test :refer [is]]
                :cljs [cljs.test :include-macros true :refer [is]])
             [clojure.spec.alpha :as s]
             [malli.core :as m]
             [malli.error :as me]))

(defn test-spec [spec x msg]
  (is (s/valid? spec x)
      (str msg (s/explain-str spec x))))

(defn test-malli-schema [sc x msg]
  (is (m/validate sc x)
      (str msg (->> x
                    (m/explain sc)
                    me/humanize))))
