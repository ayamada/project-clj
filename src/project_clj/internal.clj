(ns project-clj.internal
  (:require [project-clj.sandbox :as sandbox]))



(defmacro get-project-from-sandbox []
  (let [prev-ns *ns*]
    (in-ns 'project-clj.sandbox)
    (load-file "project.clj")
    (let [copy-of-project sandbox/project]
      (alter-var-root #'sandbox/project (fn [_] nil))
      (set! *ns* prev-ns)
      (list 'quote copy-of-project))))






