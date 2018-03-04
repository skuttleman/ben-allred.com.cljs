(ns com.ben-allred.empire.reducers.music
    (:require-macros [com.ben-allred.macros.core :refer [->map]])
    (:require [com.ben-allred.collaj.reducers :as collaj.reducers]))

(defn ^:private visible?
    ([] false)
    ([state [type]]
     (case type
         :toggle-music-player (not state)
         state)))

(defn ^:private expanded?
    ([] false)
    ([state [type]]
     (case type
         :toggle-player-expanded (not state)
         :select-song false
         state)))

(defn ^:private playing?
    ([] false)
    ([state [type]]
     (case type
         :toggle-playing (not state)
         :select-song true
         :stop-playing false
         state)))

(defn ^:private songs
    ([] [])
    ([state [type data]]
     (case type
         :receive-songs (get-in data [:body :songs])
         state)))

(defn ^:private albums
    ([] [])
    ([state [type data]]
     (case type
         :receive-albums (get-in data [:body :albums])
         state)))

(defn ^:private current-song
    ([] nil)
    ([state [type data]]
     (case type
         :select-song (:song data)
         :stop-playing nil
         state)))

(defn ^:private current-album
    ([] nil)
    ([state [type {:keys [song albums]}]]
     (case type
         :select-song (->> albums
                          (filter #(= (:album-id song) (:id %)))
                          (first))
         :stop-playing nil
         state)))

(def music (collaj.reducers/combine
               (->map visible? expanded? playing? songs albums current-song current-album)))
