# hello-world

[level-db](https://www.npmjs.com/package/level)

## Overview

"LevelDB is a fast key-value storage engine written at Google that provides an ordered mapping from string keys to string values."
[leveldb](https://www.igvita.com/2012/02/06/sstable-and-log-structured-storage-leveldb/)

LevelDB Node in Clojure Script

cljs.core.async example

macro for node-style callback (error back) http://thenodeway.io/posts/understanding-error-first-callbacks/

~~~
      (<? (level-put "age" 42)
      (<? (level-get "age")
~~~

## Setup

Clean project specific out:

~~~
    lein clean

    lein cljsbuild once

    node main.js
~~~

## License

Copyright © 2016

Distributed under the Eclipse Public License either version 1.0 or (at your option) any later version.
