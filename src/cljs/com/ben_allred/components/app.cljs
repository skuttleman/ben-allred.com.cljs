(ns com.ben-allred.components.app
    (:require [com.ben-allred.components.header :refer [header]]
              [com.ben-allred.utils.component-utils :refer [class-if]]))

(defn app [{dispatch :dispatch :as store} routing child]
    (let [{{:keys [bg-img view]} :page} store
          {{:keys [navs links]} :header} store]
        [:div
         [:div.image-container
          [:img.bg-image {:src bg-img}]]
         [header dispatch navs links view]
         [:main.app (class-if {view true bottom-margin false})
          [child store routing]]]))
