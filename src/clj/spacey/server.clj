(ns spacey.server
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.util.response :as resp]))

(defroutes app-routes
  "The `defroutes` macro defines a Ring handler function from a number of
   individual routes.  The request map is passed to each function in turn,
   until a non-nil response is returned."

  (GET "/" [] (resp/resource-response "index.html" {:root "public"}))

  ;; Serve resources on the classpath, root dir is `public` by default.
  (route/resources "/")

  (route/not-found "Page not found"))

(def app
  "Creates a handler suitable for a standard website, adding standard string
   middleware - http://weavejester.github.io/compojure/compojure.handler.html."
  (handler/site app-routes))

; (ns {{name}}.server
;   (:require [ring.adapter.jetty :as jetty]
;             [ring.middleware.resource :as resources]
;             [ring.util.response :as response])
;   (:gen-class))

; (defn render-app []
;   {:status 200
;    :headers {"Content-Type" "text/html"}
;    :body
;    (str "<!DOCTYPE html>"
;         "<html>"
;         "<head>"
;         "<link rel=\"stylesheet\" href=\"css/page.css\" />"
;         "</head>"
;         "<body>"
;         "<div>"
;         "<p id=\"clickable\">Click me!</p>"
;         "</div>"
;         "<script src=\"js/cljs.js\"></script>"
;         "</body>"
;         "</html>")})

; (defn handler [request]
;   (if (= "/" (:uri request))
;       (response/redirect "/help.html")
;       (render-app)))

; (def app
;   (-> handler
;     (resources/wrap-resource "public")))

; (defn -main [& args]
;   (jetty/run-jetty app {:port 3000}))
