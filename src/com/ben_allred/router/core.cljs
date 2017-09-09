(ns com.ben-allred.router.core
    (:require [reagent.core :as r]
              [clojure.string :as str]
              [com.ben-allred.router.path :as path]))

(defn split [r s] (str/split s r))

(defn split-query [query]
    (or (some->> query
            (next)
            (apply str)
            (split #"&")
            (map (partial split #"="))
            (map (fn [[key value]] [(keyword key) value]))
            (into {})) {}))

(defn ->routing
    ([current-path]
     {:path   current-path
      :query  (split-query (.-search js/location))
      :params {}})
    ([current-path full-path]
     (->> (path/->params current-path full-path)
         (assoc (->routing current-path) :params))))

(defn componentize [component args routing]
    (if (vector? component)
        component
        (-> [component]
            (into args)
            (into [routing]))))

(defn root [& routes]
    (let [path (r/atom (.-pathname js/location))]
        (js/setTimeout #(swap! path (constantly "/path-changed/to/this")) 2000)
        ;(js/setTimeout #(swap! path (constantly "/path-changed-again")) 3000)
        (js/setTimeout #(swap! path (constantly "/")) 4000)
        (fn [& args]
            (into
                [(->> routes
                     (map #(% path "/"))
                     (remove nil?)
                     (first)
                     (#(or % (fn [] [:div "No route defined for path: '" @path "'."]))))]
                args))))

(defn router [path & routes]
    (fn [path-atom path-prefix]
        (let [current-path @path-atom
              full-path (path/join path-prefix path)]
            (if (path/match? (path/truncate-to current-path full-path) full-path)
                (fn [& args]
                    (->> routes
                        (map #(% path-atom (path/join path-prefix path)))
                        (remove nil?)
                        (first)))))))

(defn route [path component]
    (fn [current-path path-prefix]
        (let [current-path @current-path
              full-path (path/join path-prefix path)]
            (if (path/match? current-path full-path)
                (fn [& args]
                    (componentize component args (->routing current-path full-path)))))))

(defn not-found [component]
    (fn [current-path _]
        (fn [& args]
            (componentize component args (->routing @current-path)))))
