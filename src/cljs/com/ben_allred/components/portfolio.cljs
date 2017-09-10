(ns com.ben-allred.components.portfolio)

(defn portfolio [{apps :apps}]
    [:div
     [:div.above-the-fold
      [:h1 "Portfolio"]]
     [:div.below-the-fold
      [:h2 "My Projects"]
      [:ul.projects
       (for [{:keys [link title tag-line description technologies repos]} apps]
           ^{:key link}
           [:li.project
            [:h3
             [:a.project.title {:target "_blank" :href link} title]]
            [:h4 tag-line]
            [:p description]
            [:ul.technologies
             [:li "Technologies: "]
             (for [technology technologies]
                 ^{:key technology} [:li technology])]
            [:ul.repos
             (for [{:keys [name link]} repos]
                 ^{:key name}
                 [:li.repo
                  [:a {:target "_blank" :href (str "http://github.com/skuttleman" link)} name]])]])]]])
