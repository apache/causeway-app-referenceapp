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
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;

import org.apache.causeway.commons.internal.base._NullSafe;
import org.apache.causeway.commons.internal.exceptions._Exceptions;
import org.apache.causeway.commons.internal.functions._Predicates;
import org.apache.causeway.commons.io.FileUtils;

import lombok.SneakyThrows;

record ResourceFolder(File root) {

    // -- FACTORIES

    public static ResourceFolder projectRoot() {
        return new ResourceFolder(new File("").getAbsoluteFile());
    }
    public static ResourceFolder resourceRoot() {
        return new ResourceFolder(projectRoot().relativeFile("src/main/resources"));
    }
    public static ResourceFolder testResourceRoot() {
        return new ResourceFolder(projectRoot().relativeFile("src/test/resources"));
    }
    public static ResourceFolder moduleRoot(final Class<?> anyClassInModule) {
        int nesting = 3 + (int) anyClassInModule.getPackageName().chars().filter(c->c=='.').count();
        var moduleRoot = new File(anyClassInModule.getResource("").getFile());
        for (int i = 0; i < nesting; i++) {
            moduleRoot = moduleRoot.getParentFile();
        }
        return new ResourceFolder(moduleRoot);
    }
    public static ResourceFolder testResourceRoot(final Class<?> anyClassInModule) {
        return new ResourceFolder(moduleRoot(anyClassInModule).relativeFile("src/test/resources"));
    }
    public static ResourceFolder ofFileName(final String fileName) {
        return ofFile(new File(fileName));
    }
    public static ResourceFolder ofFile(final File rootDir) {
        return new ResourceFolder(FileUtils.existingDirectoryElseFail(rootDir));
    }

    // -- UTILITIES

    /**
     * Returns a (non-recursive) {@link Stream} of (immediate) sub-folder names.
     */
    public Stream<String> streamSubFolderNames() {
        return _NullSafe.stream(root.listFiles())
                .filter(File::isDirectory)
                .map(File::getName);
    }

    public Optional<ResourceFolder> relative(final String relativeFolderName) {
        return FileUtils.existingDirectory(relativeFile(relativeFolderName))
                .map(ResourceFolder::ofFile);
    }

    public Optional<ResourceFolder> relative(final NamedPath relativePath) {
        return relative(relativePath.toString("/"));
    }

    public File relativeFile(final String relativeFileName) {
        return new File(root, relativeFileName);
    }

    public File relativeFile(final String relativeFileNameTemplate, final String arg) {
        return new File(root, String.format(relativeFileNameTemplate, arg));
    }

    public File relativeFile(final NamedPath relativePath) {
        return new File(root, relativePath.toString("/"));
    }

    public ResourceFolder makeDir(final NamedPath relativePath) {
        FileUtils.makeDir(relativeFile(relativePath));
        return relative(relativePath).orElseThrow(()->
                _Exceptions.illegalState("failed to mkdir %s", relativePath));
    }

    @SneakyThrows
    public void purgeFiles() {
        purgeFiles(file->false);
    }

    @SneakyThrows
    public void purgeFiles(final @NonNull Predicate<File> keep) {
        FileUtils.searchFiles(root, dir->true, _Predicates.not(keep), FileUtils::deleteFile);
    }

}
