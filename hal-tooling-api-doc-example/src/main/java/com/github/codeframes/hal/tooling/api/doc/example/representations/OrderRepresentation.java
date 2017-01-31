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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.codeframes.hal.tooling.api.doc.example.resources.OrderResource;
import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.link.bindings.Binding;
import com.github.codeframes.hal.tooling.link.bindings.CurieDef;
import com.github.codeframes.hal.tooling.link.bindings.CurieDefs;
import com.github.codeframes.hal.tooling.link.bindings.LinkRel;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

public class OrderRepresentation implements HalRepresentable {

    @CurieDefs({
            @CurieDef(name = "ex", value = "/docs/rels/{rel}")
    })
    private List<Curie> curies;

    @LinkRel(resource = OrderResource.class,
            method = "getOrder",
            bindings = {@Binding(name = "order_id", value = "${instance.orderId}")}
    )
    private Link selfLink;

    @LinkRel(rel = "ex:remove-order",
            resource = OrderResource.class,
            method = "removeOrder",
            bindings = {@Binding(name = "order_id", value = "${instance.orderId}")}
    )
    private Link removeOrderLink;

    private final String orderId;
    private final List<Item> items;
    private final BigDecimal cost;

    @JsonCreator
    public OrderRepresentation(@JsonProperty("items") List<Item> items,
                               @JsonProperty("cost") BigDecimal cost) {
        this.orderId = null;
        this.items = items;
        this.cost = cost;
    }

    public OrderRepresentation(String orderId, List<Item> items, BigDecimal cost) {
        this.orderId = orderId;
        this.items = items;
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

    /**
     * The list of items
     *
     * @return
     */
    @NotEmpty
    public List<Item> getItems() {
        return items;
    }

    /**
     * The cost of the order
     *
     * @return
     */
    @DecimalMax("10.5")
    @DecimalMin("5.9")
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * The date of the order
     *
     * @return
     */
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public DateTime getOrderDate() {
        return DateTime.now();
    }
}
