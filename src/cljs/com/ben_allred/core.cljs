(ns com.ben-allred.core
    (:require-macros [com.ben-allred.utils.logger :as log])
    (:require [reagent.core :as r]
              [com.ben-allred.components.app :as app]
              [com.ben-allred.components.home :as home]
              [com.ben-allred.empire.core :as emp]
              [com.ben-allred.empire.reducers.core :as red]
              [com.ben-allred.router.core :as router]))

(enable-console-print!)

(def store (emp/create-store red/root))
(r/render
    (router/root
        (fn [component routing] (emp/manage store app/component routing component))
        [["/" home/component]
         ["/:something" (fn [& args] (log/info args) [:div "NEW PATH"])]])
    (.getElementById js/document "app"))

(let [{:keys [get-state dispatch]} store]
    (js/setTimeout #(dispatch :inc) 4500)
    (js/setTimeout #(dispatch :dec) 5000))
