package com.namics.glassshowcase.nespressocapsuleinformation.helpers;

/**
 * Created by chbuehler on 17.03.14.
 */
public class Callable<T, V> implements java.util.concurrent.Callable<V> {

    @Override
    public V call() throws Exception {
        return null;
    }

    public V call(T param){
        return null;
    }
}
