(ns com.ben-allred.empire.reducers.apps)

(defn apps
    ([] [])
    ([state [type data]]
     (case type
         :receive-apps (get-in data [:body :apps])
         state)))
