(ns com.ben-allred.empire.reducers.bios)

(defn bios
    ([] [])
    ([state [type data]]
     (case type
         :receive-bios (get-in data [:body :bios])
         state)))
