(ns ageneau.utils.dom)

(defn center [dom-node]
  "return the center of the node's bounding box"
  (let [rect (.getBoundingClientRect dom-node)]
    [(+ (.-left rect) (/ (- (.-right rect) (.-left rect)) 2))
     (+ (.-top rect) (/ (- (.-bottom rect) (.-top rect)) 2))]))
