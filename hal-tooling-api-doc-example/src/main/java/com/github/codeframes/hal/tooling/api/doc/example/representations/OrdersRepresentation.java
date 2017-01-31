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
import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.Embedded;
import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.link.bindings.CurieDef;
import com.github.codeframes.hal.tooling.link.bindings.CurieDefs;
import com.github.codeframes.hal.tooling.link.bindings.LinkRel;

import java.util.List;

public class OrdersRepresentation implements HalRepresentable {

    @CurieDefs({
            @CurieDef(name = "ex", value = "/docs/rels/{rel}")
    })
    private List<Curie> curies;

    @LinkRel(resource = OrderResource.class, method = "get")
    private Link selfLink;

    private final Embedded<List<OrderRepresentation>> orders;

    public OrdersRepresentation(List<OrderRepresentation> orders) {
        this.orders = new Embedded<>("orders", orders);
    }

    public List<Curie> getCuries() {
        return curies;
    }

    public Link getSelfLink() {
        return selfLink;
    }

    public Embedded<List<OrderRepresentation>> getOrders() {
        return orders;
    }
}
