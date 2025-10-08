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
package demoapp.dom._infra.resources;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.mock.env.MockEnvironment;

import org.apache.causeway.core.config.CausewayConfiguration;
import org.apache.causeway.core.config.CausewayModuleCoreConfig;



class ResourceReaderService_Test {

    ResourceReaderService resourceReaderService;

    @BeforeEach
    void setUp() {
        resourceReaderService = new ResourceReaderService();

        var env = new MockEnvironment().withProperty("spring.profiles.active", "demo-jpa");

        resourceReaderService.markupVariableResolverService =
                new MarkupVariableResolverService(causewayConfiguration(env), env);
    }

    private CausewayConfiguration causewayConfiguration(final MockEnvironment env) {
        final var causewayRef = new AtomicReference<CausewayConfiguration.Causeway>();
        final var testPropertyValues = TestPropertyValues.of(Map.of("spring.profiles.active", "demo-jpa"));
        testPropertyValues.applyToSystemProperties(()->{
            new ApplicationContextRunner()
                .withUserConfiguration(CausewayModuleCoreConfig.class)
                .run(ctx -> {
                    var causeway = ctx.getBean(CausewayConfiguration.Causeway.class);
                    causewayRef.set(causeway);
                });
        });
        return new CausewayConfiguration(env, Optional.empty(), causewayRef.get());
    }

    @Test
    void read_with_tags() {

        // given
        var attributes = new HashMap<String, Object>();
        attributes.put("tags", "class");

        // when
        String actual = resourceReaderService.readResource(getClass(), "ResourceReaderService_Test-Test1.java", attributes);

        // then
        String expected = resourceReaderService.readResource(getClass(), "ResourceReaderService_Test-Test1-expected.java");
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void read_missing_tags() {

        // given
        var attributes = new HashMap<String, Object>();
        attributes.put("tags", "other");

        // when
        String actual = resourceReaderService.readResource(getClass(), "ResourceReaderService_Test-Test1.java", attributes);

        // then
        String expected = "";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void read_subdir_with_tags() {

        // given
        var attributes = new HashMap<String, Object>();
        attributes.put("tags", "class");

        // when
        String actual = resourceReaderService.readResource(getClass(), "subdir/ResourceReaderService_Test-Test1.java", attributes);

        // then
        String expected = resourceReaderService.readResource(getClass(), "subdir/ResourceReaderService_Test-Test1-expected.java");
        assertThat(actual).isEqualTo(expected);
    }

}
