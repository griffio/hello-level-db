(require
  '[figwheel-sidecar.repl-api :as ra])

(ra/start-figwheel! {:all-builds (figwheel-sidecar.config/get-project-builds)})
