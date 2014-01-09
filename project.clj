(defproject spacey "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :source-paths ["src/clj" "src/cljs"]

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-2080"]
                 [ring "1.2.0"]
                 [compojure "1.1.6"]]

  :plugins [[lein-cljsbuild "1.0.0"]
            [lein-ring "0.8.8"]]

  :hooks [leiningen.cljsbuild]

  :ring {:handler spacey.server/app}

  :profiles {
    :dev {
      :repl-options {:init-ns spacey.repl}}}

  ;; http://cdn.oreillystatic.com/oreilly/booksamplers/9781449327439_sampler.pdf
  ;; https://github.com/emezeske/lein-cljsbuild/blob/1.0.1/example-projects/advanced/project.clj
  ;; https://github.com/emezeske/lein-cljsbuild/blob/master/sample.project.clj
  ;; http://www.luminusweb.net/docs/clojurescript.md#advanced_compilation_and_exports
  :cljsbuild {
    :builds {
      :debug {
        :source-paths ["src/cljs"]
        :compiler {
          :output-dir "resources/public/js/debug/out"
          :output-to "resources/public/js/debug.js"
          :source-map "resources/public/js/debug.js.map"
          :optimizations :whitespace
          :pretty-print true}}
      :stage {
        :source-paths ["src/cljs"]
        :compiler {
          :output-dir "resources/public/js/stage/out"
          :output-to "resources/public/js/stage.js"
          :source-map "resources/public/js/stage.js.map"
          :optimizations :whitespace
          :pretty-print true}}
      :release {
        :source-paths ["src/cljs"]
        :compiler {
          :output-to "resources/public/js/app.js"
          :output-dir "out"
          ;; http://www.dotnetwise.com/Code/Externs/
          :externs ["resources/externs.js"]
          :optimizations :advanced}}}})
