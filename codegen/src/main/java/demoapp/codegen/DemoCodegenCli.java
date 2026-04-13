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

import java.util.HashMap;

import org.apache.causeway.commons.collections.Can;
import org.apache.causeway.commons.internal.base._Strings;

public class DemoCodegenCli {

    public static void main(final String[] args) {
        var argsModel = ArgsModel.parse(args);
        new Emitter(argsModel.projectRoot).emitAll();
    }

    // -- HELPER

    private record ArgsModel(ResourceFolder projectRoot) {

        static ArgsModel parse(final String[] args) {
            if(args.length==0) {
                printUsageAndExit();
                return null;
            }
            var map = new HashMap<String, ResourceFolder>();
            Can.ofArray(args).stream()
                .map(kv->_Strings.parseKeyValuePair(kv, '=').orElseThrow())
                .forEach(kvp->map.put(kvp.key(), ResourceFolder.ofFileName(kvp.value()) ));
            var projectRoot = map.get("projectRoot");
            if(projectRoot==null) {
                printUsageAndExit();
                return null;
            }
            return new ArgsModel(map.get("projectRoot"));
        }

        static void printUsageAndExit() {
            System.err.println(
                    """
                    please provide the project root directory as input parameter like e.g.
                    projectRoot=/path/to/project
                    """);
            System.exit(1);
        }
    }

}
