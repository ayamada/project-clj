# project-clj

Refer own `project.clj`

自分自身の `project.clj` を参照する


## Install

[![Clojars Project](http://clojars.org/jp.ne.tir/project-clj/latest-version.svg)](http://clojars.org/jp.ne.tir/project-clj)


## Usage

```sh
$ cat project.clj
(def the-url "http://example.com/")
(defproject com.example/foobar "0.1.0-SNAPSHOT"
  :url ~the-url
  :license {:name "Unlicense"
            :url "http://unlicense.org/UNLICENSE"}
  ...)
```

```clojure
(ns xxx.yyy
  (:require [project-clj.core :as project-clj]))

(project-clj/get :name) ; => "foobar"

(project-clj/get :group) ; => "com.example

(project-clj/get :version) ; => "0.1.0-SNAPSHOT"

(project-clj/get :url) ; => "http://example.com/"

(project-clj/get-in [:license :url]) ; => "http://unlicense.org/UNLICENSE"

(project-clj/get :abc) ; => nil

(project-clj/get :abc "fallback") ; => "fallback"

(project-clj/keys) ; => (:name :group :version :url :license ...)

(project-clj/get-in []) ; => {...} ; all in one, BUT BE HANDLE WITH CARE !!!

```

for cljs:

```clojure
(ns xxx.yyy
  (:require-macros [project-clj.core :as project-clj]))

...
```


## Notice

- `project-clj.core/get` and `project-clj.core/get-in` are macros.
  These are replaced to actual values in compile time.
  These values are NOT depend on `project.clj` anymore.
  - If you changed to referred entries in `profile.clj`,
    you may do `lein clean` for clean old values in `*.class`.

- `project-clj.core/get` と `project-clj.core/get-in` はマクロです。
  コンパイル時に実際の値に置換され、
  その後は `project.clj` がなくても機能します。
  - コンパイル時埋め込みである為、もし`project.clj`内の参照エントリの値を
    変更した際には`lein clean`を実行して、古い値が埋め込まれた`*.class`を
    明示的に削除した方がよいでしょう。

- Includes internal entries for leiningen. Don't care, please.

- leiningen用の内部値が含まれています。気にしないでください。

- For safety, replace from fn to symbol in value.
  If you want to get raw fn,
  you can use `get*` and `get-in*` instead of `get` and `get-in`.
  But, raw fn causes compile error in cljs.

- `project.clj`のエントリ中にfnが含まれていた場合、安全の為に
  そのfnは単なるシンボルへと置換されます。
  どうしても生fnの取得を行う必要がある場合は `get` と `get-in` の代わりに
  `get*` と `get-in*` を使ってください。
  ただし、生fnはcljs内では当然コンパイルエラーになります。


## TODO

- Not synced `lein with-profile ...` for now.

- 今のところ `lein with-profile ...` の反映はされません。


## ChangeLog

- 0.1.0 (2014-04-05)
    - Initial release





