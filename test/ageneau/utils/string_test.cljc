(ns ageneau.utils.string-test
  (:require [ageneau.utils.string :as sut]
            #?(:clj [clojure.test :as t]
               :cljs [cljs.test :as t :include-macros true])))

(def special-words
  (into (sorted-map-by #(if (= (count %1) (count %2))
                          (compare %1 %2)
                          ;; try RGBA before RGB
                          (compare (count %2) (count %1))))
        {"LOD"  ["LOD"]
         "RGBA" ["RGBA"]
         "RGB"  ["RGB"]
         "PBR"  ["PBR"]}))

(def word-regexes
  [#"^([0-9]+[A-Z])" ;; is2DArray -> :is-2d-array
   #"^([A-Z][0-9]+)" ;; remapF0OnInterfaceChange -> :remap-f0-on-interface-change
   #"^([A-Z])[A-Z]"  ;; somethingYElse -> :something-y-else
   #"^([A-Z])$"      ;; invertY -> :invert-y
   #"^([A-Z_][^A-Z0-9]+)"]) ;; customType -> :custom-type, _needDepthPrePass -> :_need-depth-pre-pass

(t/deftest tokenize-string
  (letfn [(tokenize-string [s]
            (sut/tokenize-string s {:special-words special-words
                                    :word-regexes  word-regexes}))]
    (t/is (= ["Is" "2D" "Array"] (tokenize-string "Is2DArray" )))
    (t/is (= ["Remap" "F0" "On" "Interface" "Change"] (tokenize-string "RemapF0OnInterfaceChange" )))
    (t/is (= ["Something" "Y" "Else"] (tokenize-string "SomethingYElse" )))
    (t/is (= ["Invert" "Y"] (tokenize-string "InvertY" )))
    (t/is (= ["Get" "Alpha" "From" "RGBA"] (tokenize-string "GetAlphaFromRGBA")))
    (t/is (= ["Get" "Alpha" "From" "RGB"] (tokenize-string "GetAlphaFromRGB")))
    (t/is (= ["Linear" "Specular" "LOD"] (tokenize-string "LinearSpecularLOD")))))

