(ns com.ben-allred.router.core
    (:require-macros [com.ben-allred.utils.logger :as log])
    (:require [clojure.string :as s]
              [reagent.core :as r]
              [com.ben-allred.router.path :as path]))

(defn ^:private rev-args [f] (fn [a b] (f b a)))

(def ^:private split (rev-args s/split))

(defn ^:private split-query [query]
    (or (some->> query
            (next)
            (apply str)
            (split #"&")
            (map (partial split #"="))
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
    (let [path (r/atom (.-pathname js/location))]
        (js/setTimeout (fn [] (swap! path (constantly "/new-path"))) 2000)
        (js/setTimeout (fn [] (swap! path (constantly "/"))) 3000)
        [(fn [& args] (into (apply app (find-route @path routes)) args))]))
