(def fs (js/require "fs"))

(def settings
  (.stringify js/JSON (clj->js {:x "aaa" :y "bbb"})))

(def config-file (str ".gh-release.json"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; write file

(fs.writeFile config-file settings)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; read file
(defn read-file []
  (js->clj 
    (.parse js/JSON (fs.readFileSync config-file "utf8"))))

(read-file)
(map? (read-file))
(type (read-file))
(keys (read-file))
(vals (read-file))
((read-file) "x")


(def fs (js/require "fs"))
(if (js->clj (fs.existsSync config-file)) "ok" "no")
(if (fs.existsSync config-file) "ok" "no")
(fs.existsSync config-file)


(defn load-settings []
  (js->clj
    (.parse js/JSON 
              (if (fs.existsSync config-file) (fs.readFileSync config-file) "{ }"))))

(load-settings)


(defn save-settings [settings]
  (fs.writeFileSync config-file (.stringify js/JSON (clj->js settings))))


(save-settings {:a 100 :b 200 :c 200 :d 200})


(defonce release (atom {:title "Release"}) 
  :version  "v0.0.1" 
  :owner "bcircle"
  :repo ""
  :notes "- New release ")

(@release :version)

(get {:a "hello"} :a)
({:a "hello"} :a)

