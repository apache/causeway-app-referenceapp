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
package demoapp.dom.types.primitive.longs;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Editing;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;
import org.apache.causeway.applib.annotation.PromptStyle;
import org.apache.causeway.applib.annotation.SemanticsOf;

import demoapp.dom._infra.asciidocdesc.HasAsciiDocDescription;
import demoapp.dom._infra.values.ValueHolderRepository;
import demoapp.dom.types.Samples;
import demoapp.dom.types.primitive.longs.persistence.PrimitiveLongEntity;
import demoapp.dom.types.primitive.longs.vm.PrimitiveLongVm;

@XmlRootElement(name = "root")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@Named("demo.PrimitiveLongs")
@DomainObject(nature=Nature.VIEW_MODEL, editing=Editing.ENABLED)
@DomainObjectLayout(describedAs = "Demonstrates the use of the long primitive data type in both entities and view models")
//@Log4j2
public class PrimitiveLongs implements HasAsciiDocDescription {

    @ObjectSupport public String title() {
        return "long data type";
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public PrimitiveLongVm openViewModel(final long initialValue) {
        return new PrimitiveLongVm(initialValue);
    }
    @MemberSupport public long default0OpenViewModel() {
        return samples.single();
    }

    @Collection
    public List<? extends PrimitiveLongEntity> getEntities() {
        return entities.all();
    }

    @Inject
    @XmlTransient
    ValueHolderRepository<java.lang.Long, ? extends PrimitiveLongEntity> entities;

    @Inject
    @XmlTransient
    Samples<java.lang.Long> samples;

}
