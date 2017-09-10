(ns com.ben-allred.router.core
    (:require-macros [com.ben-allred.utils.logger :as log])
    (:require [clojure.string :as s]
              [com.ben-allred.utils.string :as us]
              [reagent.core :as r]
              [com.ben-allred.router.path :as path]))

(def ^:private path (r/atom (.-hash js/location)))

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
    (let [sub-path (apply str (rest path))]
        (->> routes
            (filter (fn [[param-path route]]
                        (path/match? sub-path param-path)))
            (map (fn [[param-path route]] [route (build-routing sub-path (path/->params sub-path param-path))]))
            (first)
            (#(or % [(fn [] [:div "No route defined for '" sub-path "'"])])))))

(defn root [app routes]
    [(fn [& args] (into (apply app (find-route @path routes)) args))])

(defn ^:private goto [to on-link]
    (fn [event]
        ;(.preventDefault event)
        ;(.pushState js/history {} "" to)
        ;(set! (.-hash js/location) url)
        (reset! path to)
        (when on-link (on-link))))

(defn link-component [{:keys [to on-link] :as props} & children]
    [:a (-> props (merge {:href (str "#" to) :on-click (goto to on-link)}) (dissoc :to :on-link)) children])
