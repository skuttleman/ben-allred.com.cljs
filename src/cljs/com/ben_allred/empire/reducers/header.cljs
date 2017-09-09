(ns com.ben-allred.empire.reducers.header
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.empire.core :as emp]))

(defn ^:private navs
    ([] [])
    ([state {:keys [type body]}]
        (case type
            :receive-header (:navs body)
            state)))

(defn ^:private links
    ([] [])
    ([state {:keys [type body]}]
     (case type
         :receive-header (:links body)
         state)))

(def header (emp/combine-reducers (->map navs links)))
