# wordcut-x-server

A web-api server for wordcut-x

## Prerequisites

You will need [Leiningen][] 2.0.0 or above installed.

[leiningen]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

## Usage examples


### 1-answer word segmentation

````
$ curl -d '{"text":"กากกา"}' localhost:3000/wordseg
{"words":["\u0e01\u0e32\u0e01","\u0e01\u0e32"]}
````

### m-answer word segmentation in directed acyclic word graph

````
$ curl -d '{"text":"กากกา"}' localhost:3000/dag
[[{"s":0,"e":0,"etype":"Init"}],[{"s":0,"e":1,"etype":"Unk"}],[{"s":0,"e":2,"etype":"Dict"}],[{"s":0,"e":3,"etype":"Dict"}],[{"s":2,"e":4,"etype":"Dict"}],[{"s":3,"e":5,"etype":"Dict"}]]
````

## License

Copyright © 2018 Vee Satayamas
