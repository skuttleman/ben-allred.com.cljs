(ns com.ben-allred.components.header
    (:require [com.ben-allred.utils.component-utils :refer [class-if]]
              [com.ben-allred.router.core :refer [link-component]]))


(defn header [dispatch navs links view]
    [:header
     [:nav.top-bar.nav
      [:ul.links
       (for [{:keys [link i-class]} links]
           ^{:key link} [:li.link-element
                         [:a {:target "_blank" :href link}
                          [:i {:class-name i-class}]]])]
      [:ul.navs
       (for [{:keys [link text i-class]} navs]
           (let [new-view (keyword text)
                 active?  (= new-view view)]
               ^{:key link} [:li.nav-element (class-if {:current active?})
                             (conj (cond
                                       active? [:span]
                                       (= text "resume") [:a {:target "_top" :href link}]
                                       :else [link-component {:to link :on-link #(dispatch {:type :navigate :view new-view})}])
                                 ^{:key 0} [:i.nav-icon {:class-name i-class}]
                                 ^{:key 1} [:span.nav-text text])]))]]])
