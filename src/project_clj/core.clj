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


(defn- keyword-or-index? [v]
  (or
    (keyword? v)
    (and (integer? v) (<= 0 v))))


(def ^:private not-found-mark (gensym))




(defmacro get [k & [else]]
  (assert (keyword? k) "argument must be bare keyword")
  (let [r (clojure.core/get
            (internal/get-project-from-sandbox) k not-found-mark)]
    (if (= not-found-mark r)
      else
      (list 'quote (internal/sanitize r)))))

(defmacro get-in [ks & [else]]
  (assert (every? keyword-or-index? ks)
          "argument must be list of bare keyword or index number")
  (assert (not (empty? ks)) "project-clj.core/get-in must need keys")
  (let [r (my-get-in (internal/get-project-from-sandbox) ks not-found-mark)]
    (if (= not-found-mark r)
      else
      (list 'quote (internal/sanitize r)))))

(defmacro keys []
  (list 'quote
        (internal/sanitize
          (clojure.core/keys (internal/get-project-from-sandbox)))))




(defmacro get* [k & [else]]
  (assert (keyword? k) "argument must be bare keyword")
  (let [r (clojure.core/get
            (internal/get-project-from-sandbox) k not-found-mark)]
    (if (= not-found-mark r)
      else
      (list 'quote r))))

(defmacro get-in* [ks & [else]]
  (assert (every? keyword-or-index? ks)
          "argument must be list of bare keyword or index number")
  (assert (not (empty? ks)) "project-clj.core/get-in* must need keys")
  (let [r (my-get-in (internal/get-project-from-sandbox) ks not-found-mark)]
    (if (= not-found-mark r)
      else
      (list 'quote r))))




