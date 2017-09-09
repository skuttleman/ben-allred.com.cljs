(ns com.ben-allred.components.app
    (:require [com.ben-allred.components.header :refer [header]]))

(def links [{:link    "http://www.github.com/skuttleman"
             :i-class "fa fa-github-square"}
            {:link    "http://www.linkedin.com/in/allredbd"
             :i-class "fa fa-linkedin-square"}])

(def navs [{:link    "/"
            :text    "home"
            :i-class "fa fa-home"}
           {:link    "/bio"
            :text    "bio"
            :i-class "fa fa-info-circle"}
           {:link    "/portfolio"
            :text    "portfolio"
            :i-class "fa fa-folder-open"}
           {:link    "/music"
            :text    "music"
            :i-class "fa fa-music"}
           {:link    "https://docs.google.com/document/d/1JWDF64xzcW-KJVxNyq5I8TJ3pMdS1fVdv_oKDuMLago/export?format=pdf&id=1JWDF64xzcW-KJVxNyq5I8TJ3pMdS1fVdv_oKDuMLago"
            :text    "resume"
            :i-class "fa fa-download"}])

(defn app [{dispatch :dispatch :as store} routing child]
    (let [{{:keys [bg-img view]} :page} store]
        [:div
         [:div.image-container
          [:img.bg-image {:src bg-img}]]
         [header dispatch navs links view]
         [child store routing]]))0
