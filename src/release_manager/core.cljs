(ns release-manager.core
    (:require
      [release-manager.utility :as u]
      [reagent.core :as r]))

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

(defonce release (r/atom {:title "Project" :version  "v0.0.1" :notes "- Fixed: "}))
(def status (r/atom "___________________"))
(def running (r/atom false))

(defn update-title [title] 
  (swap! release assoc :title title)) 

(defn update-version [version] 
  (swap! release assoc :version version))

(defn update-notes [notes] 
  (swap! release assoc :notes notes))

(defn toggle [id] 
  (swap! assets update-in [id :check] not))

(defn gen-message [release assets]
  (conj release 
        {:assets (->> assets
                      (filter #(% :check))
                      (map #(% :title)))}))

(defn publish-callback [err release]
  (do 
    (swap! running (fn [] false))
    (if err (swap! status (fn [] err)) 
            (swap! status (fn [] "")))
    (js/console.log err)
    (js/console.log release)))

(defn start-publish [release assets]
  (do 
    (swap! running (fn [] true))
    (u/publish-to-github 
      (gen-message release assets)
      publish-callback)))

;; -------------------------
;; Views

(defn checkbox [file]
  [:div.checkbox {:key (file :id)}
    [:label
      [:input {:type "checkbox" 
               :on-change #(toggle (file :id))
               :checked (file :check)}]
      (file :title)]])

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
      [:button.btn.btn-default 
        {:on-click #(u/cancel)} "Cancel"]
      (if @running
        [:button.btn.btn-default.pull-right "Processing"]
        [:button.btn.btn-default.pull-right 
          {:on-click #(start-publish release assets)}
          [:span.icon.icon-github]
          "Publish"])
      [:span.pull-right @status]]])

(defn window [release, assets]
  [:div.window
    [:header.toolbar.toolbar-header
      [:div.toolbar-actions
        [:button.btn.btn-default.pull-right
          [:span.icon.icon-home]]]]
    [:div.window-content
      [:div.pane-sm {:style {:overflow-y "visible" :border-left "none"}}
        [:img {:src "images/github.png" :width 150}]]
      [:div.pane {:style {:overflow-y "visible"}}
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