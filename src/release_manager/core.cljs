(ns release-manager.core
    (:require
      [reagent.core :as r]))

;; -------------------------
;; Views

(defn checkbox [text]
  [:div.checkbox
    [:label
      [:input {:type "checkbox"}]
      text]])


(defn assets []
  [:div.form-group
   (checkbox "This is chekcbox")
   (checkbox "This is checkbox")])

(defn form[]
  [:form {:style {:padding "10px"}}
    [:div.form-group
      [:label "Title"]
      [:input {:type "text" :class "form-control" :placeholder "Title"}]]
    [:div.form-group
      [:label "Release note"]
      [:textarea.form-control {:rows 5 :placeholder "Release note"}]]
    [:div.form-group  
      [:label "Version"]
      [:input {:type "text" :class "form-control" :placeholder "Version"}]]
    (assets)])

(defn window []
  [:div.window
    [:header.toolbar.toolbar-header
      [:div.toolbar-actions
        [:button.btn.btn-default.pull-right
          [:span.icon.icon-github]]]]
    [:div.window-content
      [:div.pane {:style {:overflow-y "visible" :border-left "none"}}]
      [:div.pane
        (form)]]
    [:footer.toolbar.toolbar-footer
      [:ht.title]]])

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
