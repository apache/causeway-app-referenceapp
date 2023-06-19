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
package demoapp.dom.types.javautil.javautildate.jdo;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;

import demoapp.dom._infra.values.ValueHolderRepository;

@Profile("demo-jdo")
@Service
public class JavaUtilDateJdoEntities
extends ValueHolderRepository<Date, JavaUtilDateJdo> {

    protected JavaUtilDateJdoEntities() {
        super(JavaUtilDateJdo.class);
    }

    @Override
    protected JavaUtilDateJdo newDetachedEntity(Date value) {
        return new JavaUtilDateJdo(value);
    }

}
