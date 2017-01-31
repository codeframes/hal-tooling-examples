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
package com.github.codeframes.hal.tooling.api.doc.example.representations;

import com.github.codeframes.hal.tooling.api.doc.example.resources.OrderResource;
import com.github.codeframes.hal.tooling.api.doc.example.resources.RootResource;
import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.link.bindings.CurieDef;
import com.github.codeframes.hal.tooling.link.bindings.LinkRel;

public class RootRepresentation implements HalRepresentable {

    @CurieDef(name = "ex", value = "/docs/rels/{rel}")
    private Curie curie;

    @LinkRel(resource = RootResource.class)
    private Link selfLink;

    @LinkRel(rel = "ex:orders", resource = OrderResource.class, method = "get")
    private Link ordersLink;

    @LinkRel(rel = "ex:order", resource = OrderResource.class, method = "getOrder")
    private Link orderLink;

    @LinkRel(rel = "ex:create-order", resource = OrderResource.class, method = "createOrder")
    private Link createOrderLink;

    public Curie getCurie() {
        return curie;
    }

    public Link getSelfLink() {
        return selfLink;
    }

    public Link getOrdersLink() {
        return ordersLink;
    }

    public Link getOrderLink() {
        return orderLink;
    }

    public Link getCreateOrderLink() {
        return createOrderLink;
    }
}
