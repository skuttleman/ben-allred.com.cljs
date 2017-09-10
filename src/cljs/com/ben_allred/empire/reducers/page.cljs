(ns com.ben-allred.empire.reducers.page
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.empire.core :as emp]
              [clojure.string :as s]))

(defn ^:private view->bg-img [view]
    (case view
        :home "/images/snow.jpg"
        :bio "/images/hiking.jpg"
        :music "/images/music.jpg"
        :portfolio "/images/view.jpg"
        ""))

(defn ^:private view
    ([]
     (let [path (last (s/split (.-hash js/location) #"/"))]
         (if (or (empty? path) (= "#" path)) :home (keyword path))))
    ([state {:keys [type view]}]
     (case type
         :navigate view
         state)))

(defn ^:private bg-img
    ([] (view->bg-img :home))
    ([state {:keys [type view]}]
     (case type
         :navigate (view->bg-img view)
         state)))

(def page (emp/combine-reducers (->map view bg-img)))
