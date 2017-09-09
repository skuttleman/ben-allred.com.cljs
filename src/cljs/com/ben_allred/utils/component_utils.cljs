(ns com.ben-allred.utils.component-utils
    (:require [clojure.string :as s]
              [com.ben-allred.utils.string :as us]))

(defn class-if
    ([rules] (class-if rules {}))
    ([rules props]
        (->> rules
            (filter second)
            (map first)
            (map us/kw->str)
            (s/join " ")
            (update props :class-name (comp #(.trim %) str) " "))))
