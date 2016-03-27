(ns project-clj.internal
  (:require [project-clj.sandbox :as sandbox]
            [clojure.walk :as walk]))


(defn fn->symbol [f]
  (symbol (.getName (class f))))

;;; コンパイル済fnを即値として埋め込む事はできないので、シンボル化する
;;; またleiningenではmetaにfnを含ませる処理があるので、
;;; meta内もsanitizeする必要がある
(defn sanitize [obj]
  (walk/prewalk (fn [e]
                  (if (fn? e)
                    (fn->symbol e)
                    (if-let [m (meta e)]
                      (with-meta e (sanitize m))
                      e)))
                obj))


(defn get-project-from-sandbox []
  (let [prev-ns *ns*]
    (in-ns 'project-clj.sandbox)
    (try
      (load-file "project.clj")
      (catch java.io.FileNotFoundException e
        (binding [*out* *err*]
          (println "Warning: project.clj not found"))))
    (let [copy-of-project-map sandbox/project]
      (alter-var-root #'sandbox/project (fn [_] nil))
      (set! *ns* prev-ns)
      copy-of-project-map)))






