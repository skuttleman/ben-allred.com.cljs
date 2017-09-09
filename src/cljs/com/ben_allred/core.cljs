(ns com.ben-allred.core
    (:require [reagent.core :as r]
              [com.ben-allred.components.app :as app]
              [com.ben-allred.empire.core :as emp]
              [com.ben-allred.empire.reducers.core :as red]
              [com.ben-allred.router.core :as router]))

(enable-console-print!)

(def store (emp/create-store red/root))
(r/render
    (emp/manage store (router/root
                          (router/route "/" app/component)
                          (router/router "/path-changed"
                              (router/router "/:param1"
                                  (router/route "/:param2" (fn [_ _ routing] [:div "path changed: " routing]))))
                          #_(router/not-found [:div "sorry, bro"])))
    (.getElementById js/document "app"))

(let [{:keys [get-state dispatch]} store]
    (js/setTimeout #(dispatch :inc) 4500)
    (js/setTimeout #(dispatch :dec) 5000))
