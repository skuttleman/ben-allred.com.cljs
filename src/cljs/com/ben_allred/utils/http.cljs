(ns com.ben-allred.utils.http
    (:require-macros [cljs.core.async.macros :refer [go]])
    (:require [cljs-http.client :as http]
              [cljs.core.async :refer [<!]]
              [com.ben-allred.utils.json :as json]))

(defn ^:private process-http
    ([response on-success] (process-http response on-success #(-> nil)))
    ([response on-success on-failure]
     (if (< (:status response) 400)
         (on-success response)
         (on-failure response))))

(defn ^:private successful? [{status :status}]
    (< status 400))

(defn ^:private receive [resource {body :body}]
    [(keyword (str "receive-" (name resource)))
     {:body (json/re-key body)}])

(defn get-resource [resource]
    (fn [[dispatch]]
        (go (let [response (<! (http/get (str "/json/" (name resource) ".json")))]
                (if (successful? response)
                    (dispatch (receive resource response)))))))
