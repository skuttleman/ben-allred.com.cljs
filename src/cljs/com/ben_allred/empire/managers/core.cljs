(ns com.ben-allred.empire.managers.core
    (:require [com.ben-allred.empire.managers.music :as music]))

(defn subscribe-managers! [{:keys [dispatch subscribe]}]
    (let [music-sub (music/sub dispatch)]
        {:music (subscribe [:select-song :toggle-playing] music-sub)}))
