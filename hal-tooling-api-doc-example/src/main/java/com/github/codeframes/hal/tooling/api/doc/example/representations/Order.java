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
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.util.List;

public class Order {

    private final List<Item> items;
    private final BigDecimal cost;

    @JsonCreator
    public Order(@JsonProperty("items") List<Item> items,
                 @JsonProperty("cost") BigDecimal cost) {
        this.items = items;
        this.cost = cost;
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
}
