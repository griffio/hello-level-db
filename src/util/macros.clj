(ns util.macros)

(defmacro <? [expr]
  `(util.error/throw-err (cljs.core.async/<! ~expr)))
