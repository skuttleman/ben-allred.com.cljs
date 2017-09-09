(ns com.ben-allred.empire.core
    (:require [reagent.core :as r]))

(defn manage [{:keys [get-state dispatch]} component-tree & args]
    [(fn []
         (into [component-tree (get-state) dispatch] args))])

(defn create-store [state-reducer]
    (let [state    (r/atom (state-reducer))
          store    {:get-state #(deref state)}
          dispatch (fn dispatch [value]
                       (cond
                           (fn? value) (value (assoc store :dispatch dispatch))
                           (keyword? value) (dispatch {:type value}) ;;TODO: handle event
                           (vector? value) nil
                           (and (map? value) (:type value)) (swap! state state-reducer value)
                           :else (throw (str "Cannot dispatch value '" value "' of type: " (type value)))))]
        (assoc store :dispatch dispatch)))

(defn combine-reducers [reducers]
    {:pre [(map? reducers) (every? fn? (vals reducers))]}
    (fn
        ([] (->> reducers
                (map (fn [[key reducer]] [key (reducer)]))
                (into {})))
        ([state action] (->> reducers
                            (map (fn [[key reducer]] [key (reducer (get state key) action)]))
                            (into {})))))
