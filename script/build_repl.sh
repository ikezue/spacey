#!/bin/bash

echo -e "\nDeleting previous repl build."
rm -rf resources/public/js/repl

echo -e "\nBuilding cljs-in-cljs repl at resources/public/js/repl/core.js..."
mkdir resources/public/js/repl
cd resources/public/js/repl
export CLOJURESCRIPT_HOME=`stat -f ../../../../clojurescript`
$CLOJURESCRIPT_HOME/bin/cljsc ../../../../src/repl/core.cljs > core.js

# Prefix Google Closure and Clojure namespaces to avoid collisions with
# non-repl source.
# Hat tip to lazerwalker (https://github.com/lazerwalker).
sed -i '' 's/goog/replgoog/g' core.js
sed -i '' 's/^var goog = goog || {}/var replgoog = replgoog || {}/' out/goog/base.js
find out -type f -print0 | xargs -0 sed -i '' 's/goog\./replgoog\./g'
find out -type f -print0 | xargs -0 sed -i '' 's/cljs\./cljsrepl\./g'
find out -type f -print0 | xargs -0 sed -i '' 's/clojure\./replclojure\./g'

echo -e "\nBuild complete!"
