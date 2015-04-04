(ns project-clj.sandbox
  (:require [leiningen.core.project]
            [clojure.java.io]))


;;;; NB: このnamespace内に不要な定義を置いてはならない。
;;;;     これは上のrequireするものについても同様。


;;; project.cljによって上書きされる変数。ただし参照後はすぐnilに戻される
;;; (project.clj内のprivateな値を残さないようにする為)
(def ^:dynamic project nil)


;;; 本物のdefprojectを用いると内部情報を破壊する為、ダミーのdefprojectを用意
;;; NB: ここはleiningenのバージョンアップによって動かなくなる可能性が高い。
;;;     動かなくなったら leiningen-core/src/leiningen/core/project.clj の
;;;     defproject のところのコードを見ながら移植し直す事。
(defmacro defproject [project-name version & args]
  (let [unquote-project @#'leiningen.core.project/unquote-project
        al->am @#'leiningen.core.project/argument-list->argument-map
        make @#'leiningen.core.project/make]
    `(let [args# ~(unquote-project (al->am args))
           root# ~(.getParent (clojure.java.io/file *file*))]
       (alter-var-root (var project)
                       (fn [_#]
                         (~make args# '~project-name ~version root#))))))



