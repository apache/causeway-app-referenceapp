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
package demoapp.dom.domain.collections.CollectionLayout.defaultView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.causeway.applib.annotation.Collection;
import org.apache.causeway.applib.annotation.CollectionLayout;
import org.apache.causeway.applib.annotation.DomainObject;
import org.apache.causeway.applib.annotation.DomainObjectLayout;
import org.apache.causeway.applib.annotation.Nature;
import org.apache.causeway.applib.annotation.ObjectSupport;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import demoapp.dom._infra.asciidocdesc.HasAsciiDocDescription;
import demoapp.dom.domain.collections.CollectionLayout.defaultView.child.CollectionLayoutDefaultViewChildVm;

//tag::class[]
@Named("demo.CollectionLayoutDefaultViewPage")
@DomainObject(nature=Nature.VIEW_MODEL)
@DomainObjectLayout(cssClassFa="fa-atom")
@XmlRootElement(name = "root")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class CollectionLayoutDefaultViewPage implements HasAsciiDocDescription {

    @ObjectSupport public String title() {
        return "@CollectionLayout#defaultView";
    }

//tag::children[]
    @Collection()
    @CollectionLayout(
        defaultView = "table",          // <.>
        paged = 5
    )
    @XmlElementWrapper(name = "children")
    @XmlElement(name = "child")
    @Getter @Setter
    private List<CollectionLayoutDefaultViewChildVm> children = new ArrayList<>();
//end::children[]

//tag::more-children[]
    @Collection()
    @CollectionLayout(
        defaultView = "hidden",         // <.>
        paged = 5
    )
    @XmlElementWrapper(name = "moreChildren")
    @XmlElement(name = "child")
    @Getter @Setter
    private List<CollectionLayoutDefaultViewChildVm> moreChildren = new ArrayList<>();
//end::more-children[]

//tag::yet-more-children[]
    @Collection()
    @CollectionLayout(paged = 5)        // <.>
    @XmlElementWrapper(name = "moreChildren")
    @XmlElement(name = "child")
    @Getter @Setter
    private List<CollectionLayoutDefaultViewChildVm> yetMoreChildren = new ArrayList<>();
//end::yet-more-children[]

}
//end::class[]
