(ns com.ben-allred.router.core
    (:require-macros [com.ben-allred.utils.logger :as log])
    (:require [clojure.string :as s]
              [com.ben-allred.utils.string :as us]
              [reagent.core :as r]
              [com.ben-allred.router.path :as path]))

(def ^:private path (r/atom (.-pathname js/location)))

(defn ^:private split-query [query]
    (or (some->> query
            (next)
            (apply str)
            (us/split #"&")
            (map (partial us/split #"="))
            (map (fn [[key value]] [(keyword key) value]))
            (into {})) {}))

(defn ^:private build-routing
    ([path]
     {:path   path
      :query  (split-query (.-search js/location))
      :params {}})
    ([path params]
        (assoc (build-routing path) :params params)))

(defn ^:private find-route [path routes]
    (->> routes
        (filter (fn [[param-path route]]
                    (path/match? path param-path)))
        (map (fn [[param-path route]] [route (build-routing path (path/->params path param-path))]))
        (first)
        (#(or % [(fn [] [:div "No route defined for '" path "'"])]))))

(defn root [app routes]
    [(fn [& args] (into (apply app (find-route @path routes)) args))])

(defn ^:private goto [to on-link]
    (fn [event]
        (.preventDefault event)
        (.pushState js/history {} "" to)
        (reset! path to)
        (when on-link (on-link))))

(defn Link [{:keys [to on-link] :as props} & children]
    [:a (-> props (merge {:href "#" :on-click (goto to on-link)}) (dissoc :to :on-link)) children])
