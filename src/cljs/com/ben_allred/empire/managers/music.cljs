(ns com.ben-allred.empire.managers.music
    (:require [clojure.string :as s]))

(defn ^:private play [listener]
    (when-let [controls (.querySelector js/document ".audio-controls")]
        (.addEventListener controls "ended" listener)
        (.play controls)))

(defn ^:private pause [listener]
    (when-let [controls (.querySelector js/document ".audio-controls")]
        (.removeEventListener controls "ended" listener)
        (.pause controls)))

(defn ^:private set-song-src! [src listener]
    (pause listener)
    (when-let [controls (.querySelector js/document ".audio-controls")]
        (when-not (s/ends-with? (.-src controls) src)
            (set! (.-src controls) src))))

(defn sub [dispatch {:keys [playing? current-song]}]
    (let [listener #(dispatch [:stop-playing])]
        (when-let [{src :src} current-song]
            (if playing?
                (do (set-song-src! src listener)
                    (play listener))
                (pause listener)))))
