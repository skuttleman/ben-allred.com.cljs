(ns com.ben-allred.components.app)

(defn component [store routing child]
    [:div
     [:div "header"]
     [child store routing]
     [:div "footer"]])
