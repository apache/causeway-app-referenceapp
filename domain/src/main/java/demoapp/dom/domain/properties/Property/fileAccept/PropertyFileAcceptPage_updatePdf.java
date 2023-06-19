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
package demoapp.dom.domain.properties.Property.fileAccept;

import org.apache.causeway.applib.annotation.Action;
import org.apache.causeway.applib.annotation.ActionLayout;
import org.apache.causeway.applib.annotation.MemberSupport;
import org.apache.causeway.applib.annotation.Parameter;
import org.apache.causeway.applib.annotation.SemanticsOf;
import org.apache.causeway.applib.value.Blob;

import lombok.RequiredArgsConstructor;

@Action(semantics = SemanticsOf.IDEMPOTENT)
@ActionLayout(associateWith = "pdfProperty", sequence = "1")
@RequiredArgsConstructor
public class PropertyFileAcceptPage_updatePdf {

    private final PropertyFileAcceptPage page;

//tag::pdf[]
    @MemberSupport public PropertyFileAcceptPage act(
            @Parameter(
                fileAccept = ".pdf"                     // <.>
            )
            final Blob pdf) {
        page.setPdfProperty(pdf);
        return page;
    }
//end::pdf[]
    @MemberSupport public Blob default0Act() {
        return page.getPdfProperty();
    }

}
