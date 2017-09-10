(ns com.ben-allred.components.music)

(defn music [{:keys [dispatch music] :as state}]
    [:div
     [:div.above-the-fold
      [:h1 "Music"]]
     [:div.below-the-fold
      [:h2 "My Music"]
      (when-not (:visible? music)
          [:div.open-music-player.button
           {:on-click #(dispatch :toggle-music-player)}
           "Open Music Player"
           [:i.fa.fa-external-link]])
      [:p "I have written many songs."]
      [:p "Some of those songs have been recorded."]
      [:p "Some of those recordings are on the internet."]
      [:p "Some of those internets are available here."]]])
