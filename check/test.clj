(def model {:title "title" :assets [{:id 1 :check true} {:id 2 :check false}]})

(update-in model [:assets] (fn [x] []))

(def players [{:k 100 :v 100} {:k 200 :v 200}])
(update-in players [(first (filter #(= % 100)  players)) :k] (fn [x] 200))

;; SWAP
(def release (atom {:title "Title" :version "Version" :notes "Release notes"}))
(defn update-title [title] (swap! release  assoc :title title))
(update-title "aa")

(model :title)

;; update
(def assets (atom
             [{:title "File A" :check true}
              {:title "File B" :check false}
              {:title "File C" :check false}]))

(filter #(not= (% :title) "File A") @assets)

(into [] (concat [{:a "a"}] [{:b "b"}]))

(conj [{:a 100}] {:a 200})

(defn update-x [file]
  (filter #(not= (% :title) (file :title)) @assets)) 

(swap! assets conj (update-x {:title "File A" :check true}) {:title "B"})

(defn toggle [title check]
  (swap! assets (filter #(not= title (% :title)) @assets)))

(defn toggle [title check]
    (swap! assets (conj (filter #(not= "File A" (% :title)) @assets) {:title title :check true})))

(filter #(not= "File A" (% :title)) @assets)

(toggle "File C" true)

(if "" true false)


lein figwheel