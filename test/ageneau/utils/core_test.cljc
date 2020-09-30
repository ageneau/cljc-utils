(ns ageneau.utils.core-test
  (:require [ageneau.utils.core :as utils]
            [ageneau.utils.multimap :as mm]
            [ageneau.utils.math :as um]
            #?(:clj [clojure.test :refer [deftest testing is]]
               :cljs [cljs.test :include-macros true :refer [deftest testing is]])))


(deftest positions
  (testing "positions"
    (is (= [0 2 4]
           (utils/positions odd? [3 4 5 6 3 2])))))

(deftest split-and-reverse
  (testing "split-and-reverse"
    (is (= [3 4 5 6 7 8 9 0 1 2]
           (utils/split-and-reverse (range 10) 3)))))

(deftest doto-asser
  (testing "doto-assert"
    (is (= 5 (utils/doto-assert 5 odd?)))
    (is (= [] (utils/doto-assert [] empty? "sequence is not empty")))
    #?(:clj (is (thrown? java.lang.AssertionError (utils/doto-assert 2 odd?))))))

(deftest hierarchy
  (testing "hierarchy"
    (is (= {3 {44 {:q 3, :r 44}, 40 {:q 3, :r 40}},
            9 {4 {:q 9, :r 4}},
            2 {440 {:q 2, :r 440}},
            30 {2 {:q 30, :r 2}}}
           (utils/hierarchy [:q :r] [{:q 3 :r 44}{:q 3 :r 40}{:q 9 :r 4}{:q 2 :r 440}{:q 30 :r 2}])))))

(deftest transform-map-keys
  (testing "transform-map-keys"
    (is (= {"a-new" 33, "b-new" :test}
           (utils/transform-map-keys #(str % "-new") {"a" 33 "b" :test})))))


(deftest transform-map-vals
  (testing "transform-map-vals"
    (is (= {:a "a-test", :b "b-other"}
           (utils/transform-map-vals #(str (name %1) "-" (name %2)) {:a :test :b :other})))))

(deftest remove-nil-values
  (testing "remove-nil-values"
    (is (= {:a 33 :b 55 :d "ok"}
           (utils/remove-nil-values {:a 33 :b 55 :c nil :f nil :d "ok"})))))

(deftest rgb-to-hex
  (testing "rgb-to-hex"
    (is (= "1664ff" (utils/rgb-to-hex 22 100 255)))))

(deftest multimap
  (testing "multimap"
    (is (= {:foo #{1 2 3}}
           (mm/add {} :foo 1 :foo 2 :foo 3)))
    (is (= {:bar #{2}, :foo #{1 2 3 4}}
           (mm/mm-merge {:foo #{1 2 3}} (mm/add {} :foo 4 :bar 2))))
    (is (= {:foo #{1 3}}
           (mm/del {:foo #{1 2 3}} :foo 2)))
    (is (= {1 :foo, 3 :foo, 2 :foo}
           (mm/mm-invert {:foo #{1 2 3}})))))


(deftest math
  (testing "math functions"
    (is (= 4 (um/ilog2 16)))
    (is (= [2 4 5] (um/v-add [0 2 3] [2 2 2])))
    (is (= [0 0 5] (um/v-subtract [10 5 9] [10 5 4])))
    (is (= [6.6 8 10] (um/v-scale [3.3 4 5] 2)))))
