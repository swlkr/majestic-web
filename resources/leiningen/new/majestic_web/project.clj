(defproject {{name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "https://example.com/FIXME"
  :min-lein-version "2.6.1"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [ring/ring-core "1.5.0"]
                 [ring/ring-devel "1.5.0"]
                 [ring/ring-defaults "0.2.3"]
                 [org.postgresql/postgresql "9.4-1201-jdbc41"]
                 [ragtime/ragtime.jdbc "0.6.3"]
                 [ring/ring-mock "0.3.0"]
                 [compojure "1.5.1"]
                 [http-kit "2.2.0"]
                 [yesql "0.5.3"]
                 [environ "1.1.0"]
                 [cheshire "5.7.0"]
                 [hiccup "1.0.5"]
                 [buddy "1.3.0"]]
  :plugins [[lein-environ "1.0.3"]]
  :main {{name}}.core
  :source-paths ["src"]
  :test-paths ["test"]
  :aliases {"db/migrate"   ["run" "-m" "{{name}}.migrations/migrate"]
            "db/rollback"  ["run" "-m" "{{name}}.migrations/rollback"]
            "db/migration" ["run" "-m" "{{name}}.migrations/create"]
            "db/crud"      ["run" "-m" "{{name}}.migrations/crud"]}
  :profiles {:uberjar {:aot :all
                       :uberjar-name "{{name}}.jar"}})
