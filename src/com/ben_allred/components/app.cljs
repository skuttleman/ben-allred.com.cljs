(ns com.ben-allred.components.app)

(defn component [{:keys [i d]} dispatch routing]
    [:div (str "lalala " i "::" d routing)])
