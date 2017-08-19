(ns release-manager.utility
    (:require    
        [reagent.core :as r]))

(def electron (js/require "electron"))
(def gh (js/require "publish-release"))
(def glob (js/require "glob"))

(defn publish-to-github-x [r callback]
  (gh
    (clj->js {:token "12344"})
    (fn [err rr] (callback err rr))))

(defn publish-to-github [r callback]
  (gh
    (clj->js {:token "8a0355ee1f95ffd6720d48bcd56e71046a5b0ba1"
              :owner "wk-j"
              :repo  "dot-scripting"
              :tag (r :version)
              :name (str (r :title) " " (r :version))
              :notes (r :notes)
              :draft false
              :prerelease false
              :reuseRelease true
              :assets (r :assets)
              :target_commitish "master"})
    (fn [err rr] (callback err rr))))

(defn cancel [] 
  (electron.ipcRenderer.send "cancel"))

(def options 
  (clj->js
    {:absolute true
     :ignore ["node_modules"]}))


(defn get-files []
  (glob "**/*.zip;**/*.msi" 
         options
         (fn [err files]
           (do
             (js/console.log err)
             (js/console.log files)))))