(ns #^{:doc "A multimap is a map that permits multiple values for each
  key.  In Clojure we can represent a multimap as a map with sets as
  values."}
    ageneau.utils.multimap
  (:require [clojure.set :refer (union)]))

;; Stuart Sierra's multimap
;; https://stackoverflow.com/questions/10942607/clojure-multi-maps

(defn add
  "Adds key-value pairs the multimap."
  ([mm k v]
     (assoc mm k (conj (get mm k #{}) v)))
  ([mm k v & kvs]
     (apply add (add mm k v) kvs)))

(defn del
  "Removes key-value pairs from the multimap."
  ([mm k v]
     (let [mmv (disj (get mm k) v)]
       (if (seq mmv)
         (assoc mm k mmv)
         (dissoc mm k))))
  ([mm k v & kvs]
     (apply del (del mm k v) kvs)))

(defn mm-merge
  "Merges the multimaps, taking the union of values."
  [& mms]
  (apply (partial merge-with union) mms))

(defn mm-invert
  "Invert the multimap."
  [mm]
  (->> mm
       (mapcat (fn [[k v]]
                 (map #(vector % k) v)))
       (into (hash-map))))

