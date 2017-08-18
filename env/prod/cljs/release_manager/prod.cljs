(ns release-manager.prod
  (:require
    [release-manager.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
