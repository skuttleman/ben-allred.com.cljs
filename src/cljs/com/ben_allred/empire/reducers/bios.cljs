(ns com.ben-allred.empire.reducers.bios)

(defn bios
    ([] [])
    ([state {:keys [type body]}]
        (case type
            :receive-bios (:bios body)
            state)))
