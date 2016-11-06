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
package com.github.codeframes.hal.tooling.example.representations;

import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.example.resources.OrderResource;
import com.github.codeframes.hal.tooling.example.resources.RootResource;
import com.github.codeframes.hal.tooling.link.bindings.CurieDef;
import com.github.codeframes.hal.tooling.link.bindings.LinkRel;

import java.util.List;

public class RootRepresentation implements HalRepresentable {

    @CurieDef(name = "ord", value = "http://example.com/docs/orders/{rel}")
    private Curie orderCurie;
    @LinkRel(resource = RootResource.class)
    private Link selfLink;
    @LinkRel(rel = "ord:orders", resource = OrderResource.class, method = "getOrders")
    private Link ordersLink;
    @LinkRel(rel = "ord:order", resource = OrderResource.class, method = "getOrder")
    private Link orderLink;
    @LinkRel(rel = "ord:create-order", resource = OrderResource.class, method = "createOrder")
    private Link createOrderLink;

    private final List<Curie> curies;
    private final Link itemsLink;
    private final Link itemLink;
    private final Link createItemLink;

    public RootRepresentation(List<Curie> curies, Link itemsLink, Link itemLink, Link createItemLink) {
        this.curies = curies;
        this.itemsLink = itemsLink;
        this.itemLink = itemLink;
        this.createItemLink = createItemLink;
    }

    public Curie getOrderCurie() {
        return orderCurie;
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

    public List<Curie> getCuries() {
        return curies;
    }

    public Link getItemsLink() {
        return itemsLink;
    }

    public Link getItemLink() {
        return itemLink;
    }

    public Link getCreateItemLink() {
        return createItemLink;
    }
}
