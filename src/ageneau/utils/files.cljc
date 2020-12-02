(ns ageneau.utils.files
  (:require #?(:clj [clojure.data.json :as json])
            #?(:clj [clojure.java.io])))

(def read-json-file
  #?(:clj json/read-str
     :cljs (comp js->clj js/JSON.parse)))

#?(:clj (defn files-with-ext-in-dir [ext dir]
          (let [grammar-matcher (.getPathMatcher
                                  (java.nio.file.FileSystems/getDefault)
                                  (str "glob:*.{" ext "}"))]
            (->> dir
                 clojure.java.io/file
                 file-seq
                 (filter #(.isFile %))
                 (filter #(.matches grammar-matcher (.getFileName (.toPath %))))
                 (mapv #(.getAbsolutePath %))))))

#?(:cljs (defn read-as-text [f on-load]
           (let [fr (new js/FileReader)]
             (set! (.-onload fr) #(on-load (.. % -target -result)))
             (.readAsText fr f))))
