(ns com.ben-allred.components.app
    (:require [com.ben-allred.components.header :refer [header]]))

(defn app [{dispatch :dispatch :as store} routing child]
    (let [{{:keys [bg-img view]} :page} store
          {{:keys [navs links]} :header} store]
        [:div
         [:div.image-container
          [:img.bg-image {:src bg-img}]]
         [header dispatch navs links view]
         [child store routing]]))
