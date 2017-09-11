(ns com.ben-allred.router.path
    (:require [clojure.string :as str]))

(defn ^:private normalize [path]
    (->> (str/split path #"/")
        (remove empty?)
        (str/join "/")))

(defn ^:private segmentize [path]
    (-> path
        (normalize)
        (str/split #"/")))

(defn join [prefix path]
    (normalize (str prefix "/" path)))

(defn match? [path param-path]
    (or (= :else param-path)
        (let [[path param-path] (map segmentize [path param-path])]
            (and
                (= (count path) (count param-path))
                (->> [path param-path]
                    (apply map #(or (= %1 %2) (= (first %2) ":")))
                    (remove true?)
                    (empty?))))))

(defn ->params [path param-path]
    (->> [path param-path]
        (map segmentize)
        (apply map #(if (= (first %2) ":") [(keyword (apply str (rest %2))) %1]))
        (remove nil?)
        (into {})))

(defn truncate-to [path-1 path-2]
    (->> [path-1 path-2]
        (map segmentize)
        (apply map (fn [part-1 part-2] part-1))
        (str/join "/")))
