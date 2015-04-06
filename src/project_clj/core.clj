(ns project-clj.core
  (:refer-clojure :exclude [get get-in keys])
  (:require [project-clj.internal :as internal]))




(defmacro get [k & [else]]
  (list 'quote
        (internal/sanitize
          (clojure.core/get (internal/get-project-from-sandbox) k else))))

(defmacro get-in [ks & [else]]
  (assert (not (empty? ks)) "project-clj.core/get-in must need keys")
  (list 'quote
        (internal/sanitize
          (clojure.core/get-in (internal/get-project-from-sandbox) ks else))))

(defmacro keys []
  (list 'quote
        (internal/sanitize
          (clojure.core/keys (internal/get-project-from-sandbox)))))




(defmacro get* [k & [else]]
  (list 'quote
        (clojure.core/get (internal/get-project-from-sandbox) k else)))

(defmacro get-in* [ks & [else]]
  (assert (not (empty? ks)) "project-clj.core/get-in* must need keys")
  (list 'quote
        (clojure.core/get-in (internal/get-project-from-sandbox) ks else)))




