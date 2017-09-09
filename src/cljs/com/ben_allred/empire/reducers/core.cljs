(ns com.ben-allred.empire.reducers.core
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.empire.core :as emp]
              [com.ben-allred.empire.reducers.d :refer [d]]
              [com.ben-allred.empire.reducers.i :refer [i]]))

(def root (emp/combine-reducers (->map d i)))
