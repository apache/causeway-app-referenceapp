/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package demoapp.codegen;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.assertions._Assert;
import org.apache.causeway.commons.internal.base._Strings;
import org.apache.causeway.commons.internal.exceptions._Exceptions;

/**
 * Wraps a collection of non-null {@link String}(s) which represents the names of a (general purpose) path.
 */
record NamedPath(@NonNull Can<String> names) implements Iterable<String>, Serializable {

    // -- FACTORIES

    public static NamedPath empty() {
        return new NamedPath(Can.empty());
    }

    public static NamedPath of(final @Nullable String ... names) {
        return new NamedPath(Can.ofArray(names));
    }

    public static NamedPath of(final java.nio.file.@Nullable Path path) {
        if(path==null
                || path.getNameCount()==0)
            return empty();
        var normalized = path.normalize();
        var names = IntStream.range(0, normalized.getNameCount())
            .mapToObj(normalized::getName)
            .map(java.nio.file.Path::toString)
            .collect(Can.toCan());
        return new NamedPath(names);
    }

    public static NamedPath of(final @Nullable File file) {
        return file!=null
                ? of(file.toPath())
                : empty();
    }

    public static NamedPath parse(final @Nullable String stringified, final @NonNull String delimiter) {
        return new NamedPath(parseAsCan(stringified, delimiter));
    }

    // -- CONSTRUCTOR

    public NamedPath(final @Nullable Can<String> names) {
        this.names = names != null
                ? names.filter(_Strings::isNotEmpty)
                : Can.empty();
    }

    // -- TRANSFORMATION

    public NamedPath add(final String name) {
        return new NamedPath(names.add(name));
    }

    public NamedPath add(final @Nullable NamedPath other) {
        if(other==null
                || other.isEmpty())
            return this;
        return new NamedPath(names.addAll(other.names()));
    }

    public Optional<NamedPath> parent() {
        return isEmpty()
                ? Optional.empty()
                : Optional.of(new NamedPath(names.subCan(0, -1)));
    }
    public NamedPath parentElseFail() {
        return parent().orElseThrow(()->
            _Exceptions.unsupportedOperation("empty path has no parent"));
    }

    /**
     * Strips all the other names from the start of this path.
     * Assert this path starts with all names of the other, otherwise throws.
     * @param other - nullable
     */
    public NamedPath toRelativePath(final @Nullable NamedPath other) {
        if(other==null
                || other.isEmpty())
            return this;
        if(this.isEmpty())
            return other;
        // assert this path starts with all names of the other
        other.names().zip(this.names(), (o, t)->{
            _Assert.assertEquals(o, t);
        });
        return this.nameCount() == other.nameCount()
                ? empty()
                : subPath(other.nameCount());
    }

    public NamedPath subPath(final int fromIndex) {
        return new NamedPath(names.subCan(fromIndex));
    }

    public NamedPath subPath(final int fromIndex, final int toIndexExclusive) {
        return new NamedPath(names.subCan(fromIndex, toIndexExclusive));
    }

    // -- SHORTCUTS

    public int nameCount() {
        return names().size();
    }

    public boolean isEmpty() {
        return names().isEmpty();
    }

    public String getName(final int index) {
        return names().getElseFail(index);
    }

    public Optional<String> lastName() {
        return names().getLast();
    }

    public String lastNameElseFail() {
        return names().getLastElseFail();
    }

    public Stream<String> streamNames() {
        return names().stream();
    }

    @Override
    public Iterator<String> iterator() {
        return names().iterator();
    }

    // -- UTILITY

    public String toString(final @NonNull String delimiter) {
        return streamNames()
                .collect(Collectors.joining(delimiter));
    }

    // -- PREDICATES

    public boolean startsWith(final @Nullable NamedPath other) {
        if(other==null
                || other.isEmpty())
            return true;
        return this.names().startsWith(other.names());
    }

    // -- COMPARISON

    public static Comparator<NamedPath> comparator() {
        return comparator(String::compareTo);
    }

    public static Comparator<NamedPath> comparator(final @NonNull Comparator<String> nameComparator) {
        return Comparator.comparingInt(NamedPath::nameCount)
            .thenComparing((a, b)->{
                for (int i = 0; i < a.nameCount(); i++) {
                    int c = nameComparator.compare(a.getName(i), b.getName(i));
                    if(c!=0) return c;
                }
                return 0;
            });
    }

    // -- HELPER

    private static Can<String> parseAsCan(final String stringified, final String delimiter) {
        return _Strings.splitThenStream(stringified, delimiter)
                .collect(Can.toCan());
    }

}

