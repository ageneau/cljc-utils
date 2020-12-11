(ns ageneau.utils.dom)

(defn center
  "Return the center of the node's bounding box"
  [dom-node]
  (let [rect (.getBoundingClientRect dom-node)]
    [(+ (.-left rect) (/ (- (.-right rect) (.-left rect)) 2))
     (+ (.-top rect) (/ (- (.-bottom rect) (.-top rect)) 2))]))
