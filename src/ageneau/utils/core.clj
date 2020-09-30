(ns ageneau.utils.core)

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
