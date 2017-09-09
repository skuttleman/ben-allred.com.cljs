(ns com.ben-allred.empire.reducers.core
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.empire.core :as emp]
              [com.ben-allred.empire.reducers.bios :as bio]
              [com.ben-allred.empire.reducers.header :as header]
              [com.ben-allred.empire.reducers.page :as page]))

(def root (emp/combine-reducers (->map page/page header/header bio/bios)))
