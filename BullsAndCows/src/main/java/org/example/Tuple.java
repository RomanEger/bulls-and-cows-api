package org.example;

public class Tuple<T, K> {
    private final T t;
    private final K k;

    public T getFirst(){
        return t;
    }

    public K getSecond() {
        return k;
    }

    public Tuple(T arg1, K arg2) {
        super();
        this.t = arg1;
        this.k = arg2;
    }

    @Override
    public boolean equals(Object o) {
        Tuple<T, K> tuple = (Tuple<T, K>)o;
        return t.equals(tuple.t) && k.equals(tuple.k);
    }
}
