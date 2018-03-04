(ns com.ben-allred.empire.reducers.header
    (:require-macros [com.ben-allred.macros.core :refer [->map]]))

(defn ^:private navs
    ([] [])
    ([state [type data]]
        (case type
            :receive-header (get-in data [:body :navs])
            state)))

(defn ^:private links
    ([] [])
    ([state [type data]]
     (case type
         :receive-header (get-in data [:body :links])
         state)))

(def header (com.ben-allred.collaj.reducers/combine (->map navs links)))
