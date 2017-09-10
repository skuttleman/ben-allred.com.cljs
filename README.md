# ben-allred.com.cljs

A rewrite of [ben-allred.com](https://www.ben-allred.com) using **clojurescript** with **reagent**.

## Development

```bash
$ git clone git@github.com:skuttleman/ben-allred.com.cljs.git
$ cd ben-allred.com.cljs
$ lein figwheel
```

## Build/Deploy

```bash
$ lein do clean, cljsbuild once min, sass once
$ firebase deploy
```
