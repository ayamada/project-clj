# project-clj

Refer own `project.clj`

自分自身の `project.clj` を参照する


## Install

- https://clojars.org/jp.ne.tir/project-clj


## Usage

~~~
(ns xxx.yyy
  (:require [project-clj.core :as project-clj]))

(project-clj/get :name) ; => "project-clj"

(project-clj/get :group) ; => "jp.ne.tir"

(project-clj/get :version) ; => "0.1.0-SNAPSHOT"

(project-clj/get-in [:license :url]) ; => "http://unlicense.org/UNLICENSE"

(project-clj/get :abc) ; => nil

(project-clj/get :abc :fallback) ; => :fallback

(project-clj/keys) ; => (...)

(project-clj/get-in []) ; => {...} ; all in one, BUT BE HANDLE WITH CARE !!!

~~~

for cljs:

~~~
(ns xxx.yyy
  (:require-macros [project-clj.core :as project-clj]))

...
~~~


## Notice

- `project-clj.core/get` and `project-clj.core/get-in` are macros.
  These are replaced to actual values in compile time.
  These values are NOT depend on `project.clj` anymore.

- `project-clj.core/get` と `project-clj.core/get-in` はマクロです。
  コンパイル時に実際の値に置換され、
  その後は `project.clj` がなくても機能します。

- Includes internal entries of leiningen.

- leiningen内部用の値が含まれています。

- For safety, replace from fn to symbol in value.
  If you want to get raw fn,
  you can use `get*` `get-in*` instead of `get` `get-in`.
  But, raw fn causes compile error in cljs.

- project.cljのエントリ中にfnが含まれていた場合、安全の為に
  そのfnは単なるシンボルへと置換されます。
  どうしても生fnの取得を行う必要がある場合は `get` `get-in` の代わりに
  `get*` `get-in*` を使ってください。
  ただし、生fnはcljsでは当然コンパイルエラーになります。


## TODO

- Not synced `lein with-profile ...` for now.

- 今のところ `lein with-profile ...` の反映はされません。


## ChangeLog

- 0.1.0 (2014-04-05)
    - Initial release





