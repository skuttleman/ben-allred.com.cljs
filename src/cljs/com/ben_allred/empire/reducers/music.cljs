(ns com.ben-allred.empire.reducers.music
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.empire.core :as emp]))

(defn ^:private visible?
    ([] false)
    ([state {type :type}]
     (case type
         :toggle-music-player (not state)
         state)))

(defn ^:private expanded?
    ([] false)
    ([state {type :type}]
     (case type
         :toggle-player-expanded (not state)
         :select-song false
         state)))

(defn ^:private playing?
    ([] false)
    ([state {type :type}]
     (case type
         :toggle-playing (not state)
         :select-song true
         :stop-playing false
         state)))

(defn ^:private songs
    ([] [])
    ([state {:keys [type body]}]
     (case type
         :receive-songs (:songs body)
         state)))

(defn ^:private albums
    ([] [])
    ([state {:keys [type body]}]
     (case type
         :receive-albums (:albums body)
         state)))

(defn ^:private current-song
    ([] nil)
    ([state {:keys [type song]}]
     (case type
         :select-song song
         :stop-playing nil
         state)))

(defn ^:private current-album
    ([] nil)
    ([state {:keys [type song albums]}]
     (case type
         :select-song (->> albums
                          (filter #(= (:album-id song) (:id %)))
                          (first))
         :stop-playing nil
         state)))

(def music (emp/combine-reducers
               (->map visible? expanded? playing? songs albums current-song current-album)))
