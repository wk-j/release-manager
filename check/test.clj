(def model {:title "title" :assets [{:id 1 :check true} {:id 2 :check false}]})



(update-in model [:assets] (fn [x] []))

(def player1 {:name "Player 1" :attribs {:str 10 :int 11 :wis 9}})
(update-in player1 [:attribs :str] inc)

;; SWAP
(def release (atom {:title "Title" :version "Version" :notes "Release notes"}))
(defn update-title [title] (swap! release  assoc :title title))
(update-title "aa")


