(ns ageneau.utils.files
  (:require #?(:clj [clojure.data.json :as json])
            #?(:clj [clojure.java.io]))
  (:import (java.nio.file FileSystems)))

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

#?(:cljs (defn read-as-text
           "Read the file f asynchronously and call on-load with the content read when done."
           [f on-load]
           (let [fr (new js/FileReader)]
             (set! (.-onload fr) #(on-load (.. % -target -result)))
             (.readAsText fr f))))
