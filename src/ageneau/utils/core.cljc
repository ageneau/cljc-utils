(ns ageneau.utils.core
  (:require #?(:clj [clojure.spec.alpha :as s]
               :cljs [cljs.spec.alpha :as s])
            [clojure.walk]))

(defn positions
  "Returns a lazy sequence containing the positions at which pred
  is true for items in coll."
  [pred coll]
  (keep-indexed (fn [idx x]
                  (when (pred x)
                    idx))
                coll))

(defn split-and-reverse
  "Split a sequence at index n and reverse the front and back
  If n is negative split at index (count coll) + n and reverse front and back."
  [coll n]
  (let [n (if (neg? n) (+ (count coll) n) n)
        [front back] (split-at n coll)]
    (concat back front)))

(defn doto-assert
  "Apply f to x and call `clojure.core/assert` on the result. Return x.
  Useful as an assertion method within -> threads"
  ([x f]
   (assert (f x))
   x)
  ([x f message]
   (assert (f x) message)
   x))

(defn hierarchy
  "Takes a sequence of keys and a sequence of maps.
  Returns a tree structure as a map where the first level keys are the distinct
  values mapped to the first element of keyseq and the values are maps containing
  elements of xs matching that key.
  The leaves are elements of xs."
  [keyseq xs]
  (reduce (fn [m [ks x]]
            (assoc-in m ks x))
          {}
          (for [x xs]
            [(map x keyseq) x])))


(defn transform-map-keys
  "Recursively transforms all map keys using f as a transformation function."
  [f m]
  (clojure.walk/postwalk #(cond-> %
                            (map? %)
                            (->> (map (fn [[k v]]
                                        [(f k) v]))
                                 (into {})))
                         m))

(defn transform-map-vals
  "takes a function of 2 arguments and a map and returns a tranformed map
  where the values are the result of applying f to the key and value. "
  [f m]
  (into {} (map (fn [[k v]] [k (f k v)]) m)))

(defn remove-nil-values
  "remove all keys with nil values in the map"
  [m]
  (into {} (filter second m)))


(defn rgb-to-hex
  "convert an r g b color from 8 bit mode to an hex string."
  [r g b]
  (#?(:cljs .toString
      :clj Integer/toString)
   (+ (bit-shift-left r 16)
      (bit-shift-left g 8)
      b)
   16))


(defn check-and-throw
  "Throws an exception if `db` doesn't match the Spec `a-spec`."
  [a-spec db]
  (when-not (s/valid? a-spec db)
    (throw (ex-info (str "spec check failed: " (s/explain-str a-spec db)) {}))))

(defn ?assoc
  "Same as assoc, but skip the assoc if v is nil"
  [m & kvs]
  (->> kvs
       (partition 2)
       (filter second)
       flatten
       (apply assoc m)))
