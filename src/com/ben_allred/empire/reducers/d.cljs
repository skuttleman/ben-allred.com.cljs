(ns com.ben-allred.empire.reducers.d)

(defn d
    ([] 0)
    ([state {type :type}]
        (case type
            :dec (dec state)
            state)))
