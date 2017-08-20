(ns release-manager.utility
    (:require    
      [cljs.core.async]
      [reagent.core :as r])
    (:require-macros 
      [cljs-asynchronize.macros :as dm :refer [asynchronize]]
      [cljs.core.async.macros :as am :refer [go]]))

(def electron (js/require "electron"))
(def gh (js/require "publish-release"))
(def glob (js/require "glob"))
(def process (js/require "process"))
(def fs (js/require "fs"))

(def config-file (str ".gh-release.json"))

(defn save-settings [settings]
  (fs.writeFileSync 
    config-file 
    (.stringify js/JSON (clj->js settings))))

(defn load-settings []
  (js->clj
    (.parse js/JSON 
              (if (fs.existsSync config-file) (fs.readFileSync config-file) "{ }"))))

(defn get-token []
  (js->clj process.env.GITHUB_TOKEN))

(defn publish-to-github [r callback]
  (gh
    (clj->js {:token  (get-token)
              :owner  (r :owner)
              :repo   (r :repo)
              :tag    (r :version)
              :name   (str (r :title) " " (r :version))
              :notes  (r :notes)
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
     :ignore ["node_modules" "packages" "builds"]}))

(defn get-files [pattern callback]
  (asynchronize
    (def res (glob pattern options ...))
    (do
      (doseq [x res]
        (callback x)))))
