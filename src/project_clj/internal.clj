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


(defmacro get-project-from-sandbox []
  (let [prev-ns *ns*]
    (in-ns 'project-clj.sandbox)
    (load-file "project.clj")
    (let [copy-of-project-map sandbox/project]
      (alter-var-root #'sandbox/project (fn [_] nil))
      (set! *ns* prev-ns)
      (list 'quote copy-of-project-map))))






