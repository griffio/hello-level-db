(ns util.error)

(defn error? [x]
  (instance? js/Error x))

(defn throw-err [x]
  (if (error? x)
    (throw x)
    x))

(defn log-error [ex]
  (.log js/console "error:" ex (.-stack ex)))