(ns util.macros)

(defmacro <? [expr]
  `(util.error/throw-err (cljs.core.async/<! ~expr)))

(defmacro node->chan [f & args]
  `(let [c# (cljs.core.async/chan)]
     (~f ~@args #(cljs.core.async/onto-chan c# [(or %1 %2 true)]))
     c#))