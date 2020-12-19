(ns ageneau.utils.string
  (:require [clojure.string :as str]))

(defn tokenize-string
  "Split a string in a sequence of words.

  Arguments:
  - `s`: string to tokenize;
  - Options map containing:
  -- `special-words`: a sorted map with keys being patterns to search for and values being a vector of
  words that the pattern should be broken into;
  -- `word-regexes`: a sequence of regexes with groups; The groups need to match from the beggining of
  the string and be adjacent (no in-between group characters). E.g #\"^([A-Z])[A-Z]\" is OK but
  #\"^([A-Z])[A-Z]^([a-z])\" isn't.

  Starting at the beggining of the string:
  - See if we're looking at a pattern in the `special-words` map and if so remove that pattern from
  the beggining of the string and add the corresponding decomposition words to the result;
  - If not look for the first regex match in `words-regexes`. If found add the matched words to the
  result and remove them from the beggining of the string;
  - Else remove (ignore) the next character in the string.

  Repeat until the string is empty and return the accumulated result."
  [s {:keys [special-words word-regexes] :or {special-words {}
                                              word-regexes  []}}]
  {:pre [(seq s)]}
  (->> [[] s]
       (iterate
         (fn [[ret s]]
           (if-let [words (or (->> (map #(when (str/starts-with? s %)
                                           (get special-words %))
                                        (keys special-words))
                                   (filter seq)
                                   first)
                              (->> (map #(rest (re-find % s)) word-regexes)
                                   (filter seq)
                                   first))]
             [(into ret words) (subs s (reduce + (map count words)))]
             [ret (subs s 1)])))
       (drop-while (comp seq second))
       first
       first))
