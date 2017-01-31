/*
 * Copyright © 2016 Richard Burrow (https://github.com/codeframes)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.codeframes.hal.tooling.api.doc.example;

import com.github.codeframes.hal.tooling.apidoc.datatype.BasicDataType;
import com.github.codeframes.hal.tooling.apidoc.datatype.DataType;
import com.github.codeframes.hal.tooling.apidoc.resolvers.Resolver;
import com.github.codeframes.hal.tooling.apidoc.resolvers.datatype.DataTypeResolver;
import com.github.codeframes.hal.tooling.apidoc.resolvers.datatype.ScalarTypes;
import org.joda.time.DateTime;

import javax.annotation.Nullable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;

@Resolver(dependsOn = {"org.joda.time.DateTime"}, priority = 1)
public class DateTimeResolver implements DataTypeResolver {

    @ScalarTypes
    public static final Map<Class<?>, Set<Class<?>>> SCALAR_TYPES = new HashMap<>();

    static {
        SCALAR_TYPES.put(String.class, new HashSet<Class<?>>(Collections.singletonList(DateTime.class)));
        SCALAR_TYPES.put(Long.class, new HashSet<Class<?>>(Collections.singletonList(DateTime.class)));
    }

    @Override
    public DataType resolve(Type type,
                            List<Annotation> annotations,
                            Map<String, Map<String, Object>> constraints,
                            @Nullable Type serializedType) {
        if (type == DateTime.class) {
            if (Long.class == serializedType) {
                return new BasicDataType("integer", "int64, millis since the epoch");
            }
            return BasicDataType.DATE_TIME;
        }
        return null;
    }
}
