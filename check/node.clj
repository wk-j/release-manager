;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def fs (js/require "fs"))
(println (.readdirSync fs js/__dirname))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def fs (js/require "fs"))
(def stream (js/require "stream"))
(def ^:count eol (.-EOL (js/require "os")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(def glob (js/require "glob"))

(def options 
    (clj->js
      {:absolute true
       :ignore ["node_modules"]}))

(glob "**/*.zip" options
  (fn [err files] (js/console.log files)))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(asynchronize
    (def res (.get http "http://www.google.com" ...))
    (console/log res))