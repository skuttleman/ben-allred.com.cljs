(ns com.ben-allred.empire.reducers.i)

(defn i
    ([] 0)
    ([state {type :type}]
     (case type
         :inc (inc state)
         state)))
