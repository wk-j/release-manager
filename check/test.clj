(def model {:title "title" :assets [{:id 1 :check true} {:id 2 :check false}]})

(update-in model [:assets] (fn [x] []))

(def players [{:k 100 :v 100} {:k 200 :v 200}])
(update-in players [(first (filter #(= % 100)  players)) :k] (fn [x] 200))

;; SWAP
(def release (atom {:title "Title" :version "Version" :notes "Release notes"}))
(defn update-title [title] (swap! release  assoc :title title))
(update-title "aa")

(model :title)


