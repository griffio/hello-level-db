(ns util.macros)

(defmacro <? [expr]
"Wraps <! with exception checking. return value or throw error."
  `(util.error/throw-err (cljs.core.async/<! ~expr)))

(defmacro node->chan [f & args]
  "Call a node function for supporting error first style callbacks.
  The callback value, error or value or true, is pushed onto the result channel."
  `(let [c# (cljs.core.async/chan)]
     (~f ~@args #(cljs.core.async/onto-chan c# [(or %1 %2 true)]))
     c#))