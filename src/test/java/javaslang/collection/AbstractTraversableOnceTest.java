/*     / \____  _    ______   _____ / \____   ____  _____
 *    /  \__  \/ \  / \__  \ /  __//  \__  \ /    \/ __  \   Javaslang
 *  _/  // _\  \  \/  / _\  \\_  \/  // _\  \  /\  \__/  /   Copyright 2014-2015 Daniel Dietrich
 * /___/ \_____/\____/\_____/____/\___\_____/_/  \_/____/    Licensed under the Apache License, Version 2.0
 */
package javaslang.collection;

import org.assertj.core.api.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.stream.Collector;

/**
 * Tests all methods defined in {@link Traversable}.
 */
public abstract class AbstractTraversableOnceTest {

    protected <T> IterableAssert<T> assertThat(Iterable<T> actual) {
        return new IterableAssert<T>(actual) {
        };
    }

    protected <T> ObjectAssert<T> assertThat(T actual) {
        return new ObjectAssert<T>(actual) {
        };
    }

    protected BooleanAssert assertThat(Boolean actual) {
        return new BooleanAssert(actual) {
        };
    }

    protected DoubleAssert assertThat(Double actual) {
        return new DoubleAssert(actual) {
        };
    }

    protected IntegerAssert assertThat(Integer actual) {
        return new IntegerAssert(actual) {
        };
    }

    protected LongAssert assertThat(Long actual) {
        return new LongAssert(actual) {
        };
    }

    protected StringAssert assertThat(String actual) {
        return new StringAssert(actual) {
        };
    }

    abstract protected <T> Collector<T, ArrayList<T>, ? extends Traversable<T>> collector();

    abstract protected <T> TraversableOnce<T> empty();

    abstract protected <T> TraversableOnce<T> of(T element);

    @SuppressWarnings("unchecked")
    abstract protected <T> TraversableOnce<T> of(T... elements);

    abstract protected <T> TraversableOnce<T> ofAll(Iterable<? extends T> elements);

    abstract protected TraversableOnce<Boolean> ofAll(boolean[] array);

    abstract protected TraversableOnce<Byte> ofAll(byte[] array);

    abstract protected TraversableOnce<Character> ofAll(char[] array);

    abstract protected TraversableOnce<Double> ofAll(double[] array);

    abstract protected TraversableOnce<Float> ofAll(float[] array);

    abstract protected TraversableOnce<Integer> ofAll(int[] array);

    abstract protected TraversableOnce<Long> ofAll(long[] array);

    abstract protected TraversableOnce<Short> ofAll(short[] array);

    // -- exists

    @Test
    public void shouldBeAwareOfExistingElement() {
        assertThat(of(1, 2).exists(i -> i == 2)).isTrue();
    }

    @Test
    public void shouldBeAwareOfNonExistingElement() {
        assertThat(this.<Integer> empty().exists(i -> i == 1)).isFalse();
    }

    // -- existsUnique

    @Test
    public void shouldBeAwareOfExistingUniqueElement() {
        assertThat(of(1, 2).existsUnique(i -> i == 1)).isTrue();
    }

    @Test
    public void shouldBeAwareOfNonExistingUniqueElement() {
        assertThat(this.<Integer> empty().existsUnique(i -> i == 1)).isFalse();
    }

    @Test
    public void shouldBeAwareOfExistingNonUniqueElement() {
        assertThat(of(1, 1, 2).existsUnique(i -> i == 1)).isFalse();
    }

    // -- forAll

    @Test
    public void shouldBeAwareOfPropertyThatHoldsForAll() {
        assertThat(of(2, 4).forAll(i -> i % 2 == 0)).isTrue();
    }

    @Test
    public void shouldBeAwareOfPropertyThatNotHoldsForAll() {
        assertThat(of(2, 3).forAll(i -> i % 2 == 0)).isFalse();
    }

    // -- peek

    @Test
    public void shouldPeekNil() {
        assertThat(empty().peek(t -> {
        })).isEqualTo(empty());
    }

    @Test
    public void shouldPeekNonNilPerformingNoAction() {
        assertThat(of(1).peek(t -> {
        })).isEqualTo(of(1));
    }

    @Test
    public void shouldPeekNonNilPerformingAnAction() {
        final int[] effect = { 0 };
        final TraversableOnce<Integer> actual = of(1, 2, 3).peek(i -> effect[0] = i);
        assertThat(actual).isEqualTo(of(1, 2, 3)); // traverses all elements in the lazy case
        assertThat(effect[0]).isEqualTo(getPeekNonNilPerformingAnAction());
    }

    // returns the peek result of the specific Traversable implementation
    abstract int getPeekNonNilPerformingAnAction();

}