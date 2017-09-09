(ns com.ben-allred.utils.json
    (:require [com.ben-allred.utils.string :as s]))

(declare re-key)

(defn ^:private fix-key [[key value]]
    [(-> key (s/kw->str) (s/camel->snake) (keyword))
     (re-key value)])

(defn re-key [data]
    (cond
        (list? data) (map re-key data)
        (vector? data) (mapv re-key data)
        (set? data) (into #{} (map re-key data))
        (map? data) (into {} (map fix-key data))
        :else data))
