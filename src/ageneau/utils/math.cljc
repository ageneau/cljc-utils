(ns ageneau.utils.math)

(defn ilog2
  "compute the binary logarithm of integer n"
  [n]
  (loop [nshifts 0
         n n]
    (if (pos-int? n)
      (recur (inc nshifts) (bit-shift-right n 1))
      (dec nshifts))))

(defn v-add
  "add the 2 vectors"
  [a b]
  (mapv #(apply + %) (partition 2 (interleave a b))))

(defn v-subtract
  "subtract the 2 vectors"
  [a b]
  (mapv #(apply - %) (partition 2 (interleave a b))))

(defn v-scale
  "multiply all elements of the vector by a scalar"
  [v s]
  (mapv (partial * s) v))
