(ns com.ben-allred.empire.reducers.core
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.empire.reducers.apps :as apps]
              [com.ben-allred.collaj.reducers :as collaj.reducers]
              [com.ben-allred.empire.reducers.bios :as bios]
              [com.ben-allred.empire.reducers.header :as header]
              [com.ben-allred.empire.reducers.music :as music]
              [com.ben-allred.empire.reducers.page :as page]))

(def root (collaj.reducers/combine (->map page/page header/header bios/bios apps/apps music/music)))
