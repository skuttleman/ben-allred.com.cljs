(ns com.ben-allred.empire.reducers.page
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [clojure.string :as s]
              [com.ben-allred.collaj.reducers :as collaj.reducers]))

(defn ^:private view->bg-img [view]
    (case view
        :home "/images/snow.jpg"
        :bio "/images/hiking.jpg"
        :music "/images/music.jpg"
        :portfolio "/images/view.jpg"
        ""))

(defn ^:private bg-img
    ([] (view->bg-img :home))
    ([state [type data]]
     (case type
         :navigate (view->bg-img (:view data))
         state)))

(def page (collaj.reducers/combine (->map bg-img)))
