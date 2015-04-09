(ns project-clj.core
  (:refer-clojure :exclude [get get-in keys])
  (:require [project-clj.internal :as internal]))




;;; clojure.core/get-in can get from vec by number,
;;; but does NOT to get from seq by number!
;;; Need to get (get-in {:a '(9 8 7)} [:a 1]) ; => 8
(defn- my-get-in [m ks & [not-found]]
  (loop [sentinel (gensym)
         m m
         ks ks]
    (if (empty? ks)
      m
      (let [k (first ks)
            m2 (clojure.core/get m k sentinel)]
        (if-not (identical? m2 sentinel)
          (recur sentinel m2 (rest ks))
          (if-not (and (seq? m) (number? k))
            not-found
            (let [m2 (nth m k sentinel)]
              (if-not (identical? m2 sentinel)
                (recur sentinel m2 (rest ks))
                not-found))))))))





(defmacro get [k & [else]]
  (list 'quote
        (internal/sanitize
          (clojure.core/get (internal/get-project-from-sandbox) k else))))

(defmacro get-in [ks & [else]]
  (assert (not (empty? ks)) "project-clj.core/get-in must need keys")
  (list 'quote
        (internal/sanitize
          (my-get-in (internal/get-project-from-sandbox) ks else))))

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
        (my-get-in (internal/get-project-from-sandbox) ks else)))




