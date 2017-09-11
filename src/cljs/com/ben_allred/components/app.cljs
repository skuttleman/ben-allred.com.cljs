(ns com.ben-allred.components.app
    (:require [com.ben-allred.components.header :refer [header]]
              [com.ben-allred.components.player :refer [player]]
              [com.ben-allred.utils.component-utils :refer [class-if]]))

(defn app [{:keys [dispatch music] :as store} routing child]
    (let [{{:keys [bg-img]} :page} store
          {{:keys [navs links]} :header} store
          view (keyword (apply str (or (next (:path routing)) ["home"])))]
        [:div
         [:div.image-container
          [:img.bg-image {:src bg-img}]]
         [header dispatch navs links view]
         [:main.app (class-if {view true :bottom-margin (:visible? music)})
          [child store routing]]
         [player music dispatch]]))
