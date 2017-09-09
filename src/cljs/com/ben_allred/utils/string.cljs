(ns com.ben-allred.utils.string
    (:require [clojure.string :as s]))

(defn split [re s] (s/split s re))

(defn kw->str [k] (str (name k)))
