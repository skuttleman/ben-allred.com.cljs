(ns com.ben-allred.components.player
    (:require [com.ben-allred.utils.component-utils :refer [class-if]]))

(defn ^:private select-song [song albums]
    (fn [{dispatch :dispatch}]
        (dispatch {:type :select-song :song song :albums albums})))

(defn player [{:keys [visible? expanded? playing? songs albums current-song current-album]} dispatch]
    [:div.music-player
     (when (and visible? expanded?)
         [:div.song-list
          [:ul.songs
           (for [{title :title :as song} songs
                 :let [selected? (= current-song song)
                       on-click  #(dispatch (select-song song albums))]]
               ^{:key title}
               [:li.song (class-if {:selected selected?} {:on-click on-click}) title])]])
     (when visible?
         [:div.controls
          [:div.now-playing {:on-click #(dispatch :toggle-player-expanded)}
           (apply str (take 15 (or (:title current-song) "Select a Song")))
           [:i.fa (class-if {:fa-caret-up (not expanded?) :fa-caret-down expanded?})]]
          (when current-song
              [:div.button-container
               [:div.button.play-pause {:on-click #(dispatch :toggle-playing)}
                [:i.fa (class-if {:fa-play (and (not playing?) current-song) :fa-pause playing?})]]])
          (when current-album
              [:a {:target "_blank" :href (:itunes current-album)}
               [:img.album-cover {:src (:art current-album) :alt "album cover"}]])
          (when current-album
              [:a {:target "_blank" :href (:itunes current-album)}
               [:div.button.itunes
                [:i.fa.fa-apple]
                [:span "iTunes"]]])
          [:div.button-container
           [:div.button.close {:on-click #(dispatch :toggle-music-player)}
            [:i.fa.fa-times]]]])])
