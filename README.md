# project-clj

Refer own `project.clj`

自分自身の `project.clj` を参照する


## Usage

~~~
(ns xxx.yyy
  (:require [project-clj.core :as project-clj]))

(project-clj/get :name) ; => "project-clj"

(project-clj/get :group) ; => "jp.ne.tir"

(project-clj/get :version) ; => "0.1.0-SNAPSHOT"

(project-clj/get-in [:license :url]) ; => "http://unlicense.org/UNLICENSE"

(project-clj/get :hoge :fallback) ; => :fallback

(project-clj/get-in []) ; => {...} ; all in one map (for debug / dangerous)

~~~

for ClojureScript:

~~~
(ns xxx.yyy
  (:require-macros [project-clj.core :as project-clj]))

...
~~~


## Notice

- `project-clj.core/get` and `project-clj.core/get-in` are macros.
  These are replaced to actual values in compile time.

- `project-clj.core/get` と `project-clj.core/get-in` はマクロです。
  コンパイル時に実際の値に置換されます。


## TODO

- Not synced `lein with-profile ...` now.

- `lein with-profile ...` の反映がされないようです。時間があれば対応します。



