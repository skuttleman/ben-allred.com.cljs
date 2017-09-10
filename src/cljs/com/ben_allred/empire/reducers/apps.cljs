(ns com.ben-allred.empire.reducers.apps)

(defn apps
    ([] [])
    ([state {:keys [type body]}]
        (case type
            :receive-apps (:apps body)
            state)))
