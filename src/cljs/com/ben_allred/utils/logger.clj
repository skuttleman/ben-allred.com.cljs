(ns com.ben-allred.utils.logger)

(defmacro debug [& args] `(.log js/console "%c[DEBUG]:" "background: #ccc" ~@args))
(defmacro info [& args] `(.info js/console "[INFO]:" ~@args))
(defmacro warn [& args] `(.warn js/console "%c[WARN]:" "background: #ffa" ~@args))
(defmacro error [& args] `(.error js/console "%c[ERROR]:" "background: #a00; color: #fff" ~@args))

(defmacro spy [form]
    `(let [f# (quote ~form) result# ~form]
         (do (.log js/console (str "%c SPY: " f#) "background: #000; color: #fff" result#)
             result#)))

