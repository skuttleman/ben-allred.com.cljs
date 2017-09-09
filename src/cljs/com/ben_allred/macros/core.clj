(ns com.ben-allred.macros.core)

(defmacro ->map [& vars]
    (->> vars
        (map (fn [v] [(keyword (name v)) v]))
        (into {})))
