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

- For safety, replace from fn to symbol in value.

- `project.clj`のエントリ中にfnが含まれていた場合、安全の為に
  そのfnは単なるシンボルへと置換されます。

- Includes internal entries for leiningen.

- leiningen用の内部値が含まれています。

- You should not write a code like `(:url (project-clj/get :license))`.
  It expand to
  `(:url {:name "Unlicense", :url "http://unlicense.org/UNLICENSE"})`.
  - This code should be `(project-clj/get-in [:license :url])`.
    It expand to `"http://unlicense.org/UNLICENSE"`.

- `project.clj`には、上記のleiningen用の内部値も含め、
  「不特定多数に公開されてほしくない値」が含まれる可能性があります。
  余分な情報が`*.class`に含まれてしまわないように注意してください。
  - 例えば `(:url (project-clj/get :license))` は
    `(:url {:name "Unlicense", :url "http://unlicense.org/UNLICENSE"})`
    のように展開される為、別に使う必要のない `:name` のエントリまで
    `*.class` に含まれてしまいます。
    これは動作には全く問題ありませんが、dumpする事で情報を読めてしまいます。
    なるべく `(project-clj/get-in [:license :url])` のように指定してください。
    - この例では別にセキュリティ的な問題にはならないですが、
      プライベートリポジトリの設定、パスワード類、ビルドPCのpath情報、
      等々が流出してしまわないように気をつけてください。


## TODO

- Not synced `lein with-profile ...` for now.

- 今のところ `lein with-profile ...` の反映はされません。


## ChangeLog

- 0.1.0 (2014-04-05)
    - Initial release





