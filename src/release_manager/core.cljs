(ns release-manager.core
    (:require
      [reagent.core :as r]))

;; -------------------------
;; Views

(defn checkbox [file]
  [:div.checkbox 
    [:label
      [:input {:type "checkbox" :checked (file :check)}]
      (file :title)]])

(defn assets [files]
  (map checkbox files))

(defn form[]
  [:form {:style {:padding "15px"}}
    [:div.form-group
      [:label "Title"]
      [:input {:type "text" :class "form-control" :placeholder "Title"}]]
    [:div.form-group
      [:label "Release note"]
      [:textarea.form-control {:rows 3 :placeholder "Release note"}]]
    [:div.form-group  
      [:label "Version"]
      [:input {:type "text" :class "form-control" :placeholder "Version"}]]
    [:div.form-group
      [:label "Assets"]
      [:div  {:style {:padding-left "20px"}}
        (assets [{:title "File1" :check true}
                 {:title "File2" :check false}
                 {:title "File3" :check false}])]]])

(defn footer []
  [:footer.toolbar.toolbar-footer
    [:div.toolbar-actions
      [:button.btn.btn-default "Cancel"]
      [:button.btn.btn-default.pull-right 
        [:span.icon.icon-github]
        ". Publish New Release"]]])
        

(defn window []
  [:div.window
    [:header.toolbar.toolbar-header
      [:div.toolbar-actions
        [:button.btn.btn-default.pull-right
          [:span.icon.icon-github]]]]
    [:div.window-content
      [:div.pane {:style {:overflow-y "visible" :border-left "none"}}
        [:img {:src "images/github.png"}]]
      [:div.pane
        (form)]]
    (footer)])

(defn home-page []
  [:div 
    (window)
    [:h2 "Welcome to Reagent"]])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))