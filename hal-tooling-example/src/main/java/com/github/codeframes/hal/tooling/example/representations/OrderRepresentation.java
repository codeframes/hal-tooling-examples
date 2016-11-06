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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.example.resources.OrderResource;
import com.github.codeframes.hal.tooling.link.bindings.Binding;
import com.github.codeframes.hal.tooling.link.bindings.CurieDef;
import com.github.codeframes.hal.tooling.link.bindings.CurieDefs;
import com.github.codeframes.hal.tooling.link.bindings.LinkRel;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.List;

public class OrderRepresentation implements HalRepresentable {

    @CurieDefs({
            @CurieDef(name = "ord", value = "http://example.com/docs/orders/{rel}")
    })
    private List<Curie> curies;
    @LinkRel(resource = OrderResource.class, method = "getOrder", bindings = {@Binding(name = "order_id", value = "${instance.orderId}")})
    private Link selfLink;
    @LinkRel(rel = "ord:remove-order", resource = OrderResource.class, method = "removeOrder", bindings = {@Binding(name = "order_id", value = "${instance.orderId}")})
    private Link removeOrderLink;

    private final String orderId;
    private final List<OrderItem> orderItems;
    private final BigDecimal cost;

    @JsonCreator
    public OrderRepresentation(@JsonProperty("items") List<OrderItem> orderItems,
                               @JsonProperty("cost") BigDecimal cost) {
        this.orderId = null;
        this.orderItems = orderItems;
        this.cost = cost;
    }

    public OrderRepresentation(String orderId, List<OrderItem> orderItems, BigDecimal cost) {
        this.orderId = orderId;
        this.orderItems = orderItems;
        this.cost = cost;
    }

    public List<Curie> getCuries() {
        return curies;
    }

    public Link getSelfLink() {
        return selfLink;
    }

    public Link getRemoveOrderLink() {
        return removeOrderLink;
    }

    @JsonIgnore
    public String getOrderId() {
        return orderId;
    }

    @NotEmpty
    @JsonProperty("items")
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
