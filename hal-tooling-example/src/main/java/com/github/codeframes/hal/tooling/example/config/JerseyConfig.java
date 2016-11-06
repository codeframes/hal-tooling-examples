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
package com.github.codeframes.hal.tooling.example.config;

import com.github.codeframes.hal.tooling.example.providers.ConstraintViolationExceptionMapper;
import com.github.codeframes.hal.tooling.example.providers.ObjectMapperResolver;
import com.github.codeframes.hal.tooling.example.resources.ImageResource;
import com.github.codeframes.hal.tooling.example.resources.ItemResource;
import com.github.codeframes.hal.tooling.example.resources.OrderResource;
import com.github.codeframes.hal.tooling.example.resources.RootResource;
import com.github.codeframes.hal.tooling.link.bindings.jaxrs.providers.LinkInjectorInterceptor;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(RootResource.class);
        register(OrderResource.class);
        register(ItemResource.class);
        register(ImageResource.class);
        register(ConstraintViolationExceptionMapper.class);
        register(ObjectMapperResolver.class);
        register(LinkInjectorInterceptor.class);
    }
}