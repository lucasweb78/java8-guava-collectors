/*
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *   the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *   an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *   specific language governing permissions and limitations under the License.
 *
 *   Copyright 2016 the original author or authors.
 *
 */

package uk.co.lucasweb.stream;

import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * Custom {@link Collector} that accumulates the input Guava {@link ImmutableList} collections into a single {@link ImmutableList}.
 *
 * @param <E>
 *         the type of elements in the list
 * @author Richard Lucas
 */
public class ImmutableListsCollector<E> implements Collector<Collection<E>, ImmutableList.Builder<E>, ImmutableList<E>> {

    /**
     * Returns a new instance of this collector.
     *
     * @param <E>
     *         the type of elements in the lists
     * @return a new instance of this collector.
     */
    public static <E> Collector<Collection<E>, ?, ImmutableList<E>> toImmutableList() {
        return new ImmutableListsCollector<>();
    }

    @Override
    public Supplier<ImmutableList.Builder<E>> supplier() {
        return ImmutableList::builder;
    }

    @Override
    public BiConsumer<ImmutableList.Builder<E>, Collection<E>> accumulator() {
        return ImmutableList.Builder::addAll;
    }

    @Override
    public BinaryOperator<ImmutableList.Builder<E>> combiner() {
        return (left, right) -> {
            left.addAll(right.build());
            return left;
        };
    }

    @Override
    public Function<ImmutableList.Builder<E>, ImmutableList<E>> finisher() {
        return ImmutableList.Builder::build;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return EnumSet.of(Characteristics.UNORDERED);
    }

}
