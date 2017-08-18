(ns release-manager.core
    (:require
      ;;[left-pad]
      [reagent.core :as r]))

;; Models
(def release (r/atom {:title "Title" 
                      :version "Version" 
                      :notes "Release notes"
                      :assets [{:title "File A" :check true}
                               {:title "File B" :check false}
                               {:title "File C" :check false}]}))

;; -------------------------
;; Views

(def electron  (js/require "electron"))

(defn cancel []
  (electron.ipcRenderer.send "cancel"))

(defn get-files []
  (electron.ipcRenderer.send "getFiles"))

(defn checkbox [file]
  [:div.checkbox 
    [:label
      [:input {:type "checkbox" :checked (file :check)}]
      (file :title)]])

(defn assets [files]
  (map checkbox files))

(defn form[release]
  [:form {:style {:padding "15px"}}
    [:div.form-group
      [:label "Title"]
      [:input {:type "text" :class "form-control" :placeholder "Title" 
               :value (release :title)}]]
    [:div.form-group
      [:label "Release note"]
      [:textarea.form-control {:rows 3 :placeholder "Release note" 
                               :value (release :notes)}]]
    [:div.form-group  
      [:label "Version"]
      [:input {:type "text" :class "form-control" :placeholder "Version" 
               :value (release :version)}]]
    [:div.form-group
      [:label "Assets"]
      [:div  {:style {:padding-left "20px"}}
        (assets (release :assets))]]])

(defn footer []
  [:footer.toolbar.toolbar-footer
    [:div.toolbar-actions
      [:button.btn.btn-default {:on-click #(cancel)} "Cancel"]
      [:button.btn.btn-default.pull-right  {:on-click #(get-files)}
        [:span.icon.icon-github]
        ". Publish New Release"]]])

(defn window [release]
  [:div.window
    [:header.toolbar.toolbar-header
      [:div.toolbar-actions
        [:button.btn.btn-default.pull-right
          [:span.icon.icon-github]]]]
    [:div.window-content
      [:div.pane {:style {:overflow-y "visible" :border-left "none"}}
        [:img {:src "images/github.png"}]]
      [:div.pane
        (form release)]]
    (footer)])

(defn home-page []
  [:div 
    (window @release)])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))