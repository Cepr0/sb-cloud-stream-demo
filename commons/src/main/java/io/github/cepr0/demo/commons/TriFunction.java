package io.github.cepr0.demo.commons;

@FunctionalInterface
public interface TriFunction<X, Y, Z, R> {
	R apply(X x, Y y, Z z);
}
