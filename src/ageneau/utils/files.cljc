(ns ageneau.utils.files
  (:require #?(:clj [clojure.data.json :as json])
            #?(:clj [clojure.java.io]))
  #?(:clj (:import (java.nio.file Paths
                                  FileSystems
                                  Files)
                   (java.nio.file.attribute FileAttribute))))

(def read-json-file
  #?(:clj json/read-str
     :cljs (comp js->clj js/JSON.parse)))

#?(:clj (defn files-with-ext-in-dir
          "Return the list of paths for files that match the extension ext in directory dir."
          [ext dir]
          (let [grammar-matcher (.getPathMatcher
                                  (FileSystems/getDefault)
                                  (str "glob:*.{" ext "}"))]
            (->> dir
                 .toFile
                 file-seq
                 (filter #(.isFile %))
                 (filter #(.matches grammar-matcher (.getFileName (.toPath %))))
                 (mapv #(.getAbsolutePath %))))))

#?(:clj (defn paths-get
          "Converts a path string, or a sequence of strings that when joined form a path string, to a Path."
          [& coll]
          {:pre [(and (>= (count coll) 1) (every? string? coll))]}
          (.toFile (Paths/get (first coll) (into-array (rest coll))))))

#?(:clj (defn mk-temp-dir
          "Creates a new directory in the default temporary-file directory, using the given prefix to generate its name.
  Return a Path object."
          [prefix]
          (Files/createTempDirectory prefix (into-array FileAttribute []))))

#?(:cljs (defn read-as-text
           "Read the file f asynchronously and call on-load with the content read when done."
           [f on-load]
           (let [fr (new js/FileReader)]
             (set! (.-onload fr) #(on-load (.. % -target -result)))
             (.readAsText fr f))))
