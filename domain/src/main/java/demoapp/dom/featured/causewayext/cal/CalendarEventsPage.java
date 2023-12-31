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
package demoapp.dom.featured.causewayext.cal;

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
import org.apache.causeway.extensions.fullcalendar.applib.value.CalendarEvent;

import lombok.NoArgsConstructor;

import demoapp.dom._infra.asciidocdesc.HasAsciiDocDescription;
import demoapp.dom._infra.values.ValueHolderRepository;
import demoapp.dom.featured.causewayext.cal.persistence.CausewayCalendarEventEntity;
import demoapp.dom.featured.causewayext.cal.vm.CausewayCalendarEventVm;
import demoapp.dom.types.Samples;

@Named("demo.CausewayCalendarEvents")
@DomainObject(nature=Nature.VIEW_MODEL, editing=Editing.ENABLED)
@DomainObjectLayout(cssClassFa="far fa-calendar-alt")
@XmlRootElement(name = "root")
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
@NoArgsConstructor
public class CalendarEventsPage implements HasAsciiDocDescription {

    @ObjectSupport public String title() {
        return "CalendarEvent data type";
    }

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(promptStyle = PromptStyle.DIALOG_MODAL)
    public CausewayCalendarEventVm openViewModel(final CalendarEvent initialValue) {
        return new CausewayCalendarEventVm(initialValue);
    }
    @MemberSupport public CalendarEvent default0OpenViewModel() {
        return samples.single();
    }

    @Collection
    public List<? extends CausewayCalendarEventEntity> getEntities() {
        return entities.all();
    }

    @Inject
    @XmlTransient
    ValueHolderRepository<CalendarEvent, ? extends CausewayCalendarEventEntity> entities;

    @Inject
    @XmlTransient
    Samples<CalendarEvent> samples;

}
