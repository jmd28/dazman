package com.mygdx.game;

class Pair<K,V> {
    K fst;
    V snd;

    static <K,V> Pair<K,V> of(K fst, V snd) {
        Pair<K,V> ret = new Pair<>();
        ret.fst=fst;
        ret.snd=snd;
        return ret;
    }
}