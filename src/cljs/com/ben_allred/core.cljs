(ns com.ben-allred.core
    (:require-macros [com.ben-allred.utils.logger :as log])
    (:require [reagent.core :as r]
              [com.ben-allred.components.app :refer [app]]
              [com.ben-allred.components.bio :refer [bio]]
              [com.ben-allred.components.home :refer [home]]
              [com.ben-allred.components.music :refer [music]]
              [com.ben-allred.components.portfolio :refer [portfolio]]
              [com.ben-allred.empire.reducers.core :as red]
              [com.ben-allred.router.core :as router]
              [com.ben-allred.utils.http :as http]
              [com.ben-allred.empire.managers.music :as mgr]
              [com.ben-allred.collaj.enhancers :as collaj.enhancers]
              [com.ben-allred.collaj.core :as collaj]))

(enable-console-print!)

(def store (collaj/create-custom-store r/atom
                                       red/root
                                       collaj.enhancers/with-fn-dispatch
                                       collaj.enhancers/with-watchers))

(defn ^:private manage [{:keys [get-state dispatch]} app & args]
    [(fn [] (into [app (assoc (get-state) :dispatch dispatch)] args))])

(r/render
    (router/root
        (fn [component routing] (manage store app routing component))
        [["/" home]
         ["/bio" bio]
         ["/music" music]
         ["/portfolio" portfolio]
         [:else (router/redirect "/")]])
    (.getElementById js/document "app"))

(defn init []
    (let [{:keys [dispatch watch]} store]
        (dispatch (http/get-resource :albums))
        (dispatch (http/get-resource :apps))
        (dispatch (http/get-resource :bios))
        (dispatch (http/get-resource :header))
        (dispatch (http/get-resource :songs))
        (watch [:music] (fn [_ music]
                            (mgr/sub dispatch music)))))


(init)
