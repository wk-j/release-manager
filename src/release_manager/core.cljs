(ns release-manager.core
    (:require
      [reagent.core :as r]))

;; Models
(defonce assets (r/atom (sorted-map)))
(defonce counter (r/atom 0))

(defn add-asset [title]
                (let [id (swap! counter inc)]
                  (swap! assets assoc id {:id id :title title :check false})))

(defonce init (do
                (add-asset "Rename Cloact to Reagent")
                (add-asset "Add undo demo")
                (add-asset "Make all rendering async")
                (add-asset "Allow any arguments to component functions"))) 

(def release (r/atom {:title "" :version  "" :notes ""}))

;; Controls
(def electron  (js/require "electron"))

(defn cancel []
  (electron.ipcRenderer.send "cancel"))

(defn get-files []
  (let [files (electron.ipcRenderer.send "getFiles")]
    (swap! assets (map #({:title % :check false}) files))))

(defn publish-release [release assets]
  (electron.ipcRenderer.send 
      "publishRelease" 
      (clj->js {:title (release :title)
                :version (release :version)
                :notes (release :notes)
                :assets (->> assets 
                             (filter #(%)) 
                             (map #(% :title)))})))


(defn update-title [title]
  (swap! release assoc :title title)) 

(defn update-version [version] 
  (swap! release assoc :version version))

(defn update-notes [notes]
  (swap! release assoc :notes notes))

(defn fill [title check]
  (filter (fn [x] (= title (x :title))) assets))


(defn toggle [id] (swap! assets update-in [id :check] not))

;; -------------------------
;; Views

(defn checkbox [file]
  [:div.checkbox 
    [:label
      [:input {:type "checkbox" 
               :on-change #(toggle (file :id))
               :checked (file :check)}]
      (str (file :check) " " (file :title))]])

(defn form[release, assets]
  [:form {:style {:padding "15px"}}
    [:div.form-group
      [:label "Title"]
      [:input {:type "text" :class "form-control" :placeholder "Title" 
               :on-change #(update-title (-> % .-target .-value))
               :value (release :title)}]]
    [:div.form-group
      [:label "Release note"]
      [:textarea.form-control {:rows 3 :placeholder "Release note" 
                               :on-change #(update-notes (-> % .-target .-value))
                               :value (release :notes)}]]
    [:div.form-group  
      [:label "Version"]
      [:input {:type "text" :class "form-control" :placeholder "Version" 
               :on-change #(update-version (-> % .-target .-value))
               :value (release :version)}]]
    [:div.form-group
      [:label "Assets"]
      [:div  {:style {:padding-left "20px"}}
        (map checkbox assets)]]])

(defn footer [release assets]
  [:footer.toolbar.toolbar-footer
    [:div.toolbar-actions
      [:button.btn.btn-default {:on-click #(cancel)} "Cancel"]
      [:button.btn.btn-default.pull-right  {:on-click #(publish-release release assets)}
        [:span.icon.icon-github]
        ". Publish New Release"]]])

(defn window [release, assets]
  [:div.window
    [:header.toolbar.toolbar-header
      [:div.toolbar-actions
        [:button.btn.btn-default.pull-right
          [:span.icon.icon-github]]]]
    [:div.window-content
      [:div.pane {:style {:overflow-y "visible" :border-left "none"}}
        [:img {:src "images/github.png"}]]
      [:div.pane
        (form release assets)]]
    (footer release assets)])

(defn home-page []
  [:div 
    (window @release (vals @assets))])

;; -------------------------
;; Initialize app

(defn mount-root []
  (r/render [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-root))