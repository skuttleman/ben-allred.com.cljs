(ns com.ben-allred.core
    (:require-macros [com.ben-allred.utils.logger :as log])
    (:require [reagent.core :as r]
              [com.ben-allred.components.app :refer [app]]
              [com.ben-allred.components.home :refer [home]]
              [com.ben-allred.empire.core :as emp]
              [com.ben-allred.empire.reducers.core :as red]
              [com.ben-allred.router.core :as router]
              [com.ben-allred.utils.http :as http]))

(enable-console-print!)

(def store (emp/create-store red/root))

(r/render
    (router/root
        (fn [component routing] (emp/manage store app routing component))
        [["/" home]
         ["/:something" (fn [] [:div "NEW PATH"])]])
    (.getElementById js/document "app"))

(let [{dispatch :dispatch} store]
    (dispatch (http/get-resource "albums"))
    (dispatch (http/get-resource "apps"))
    (dispatch (http/get-resource "bios"))
    (dispatch (http/get-resource "header"))
    (dispatch (http/get-resource "songs")))
