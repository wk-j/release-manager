(ns ^:figwheel-no-load release-manager.dev
  (:require
    [release-manager.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
