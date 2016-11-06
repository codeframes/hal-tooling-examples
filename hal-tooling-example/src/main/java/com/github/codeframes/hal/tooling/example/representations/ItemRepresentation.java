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
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.json.LinkSerialization;
import org.hibernate.validator.constraints.NotBlank;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class ItemRepresentation implements HalRepresentable {

    private final List<Curie> curies;
    private final List<Link> links;
    private final List<Link> imageLinks;
    private final String name;
    private final BigDecimal cost;

    @JsonCreator
    public ItemRepresentation(@JsonProperty("name") String name,
                              @JsonProperty("cost") BigDecimal cost) {
        this(null, null, name, cost);
    }

    public ItemRepresentation(List<Link> links, List<Link> imageLinks, String name, BigDecimal cost) {
        this(Collections.<Curie>emptyList(), links, imageLinks, name, cost);
    }

    public ItemRepresentation(List<Curie> curies, List<Link> links, List<Link> imageLinks, String name, BigDecimal cost) {
        this.curies = curies;
        this.links = links;
        this.imageLinks = imageLinks;
        this.name = name;
        this.cost = cost;
    }

    public List<Curie> getCuries() {
        return curies;
    }

    public List<Link> getLinks() {
        return links;
    }

    @LinkSerialization(LinkSerialization.LinkSerializationMethod.EXPLICIT)
    public List<Link> getImageLinks() {
        return imageLinks;
    }

    @NotBlank
    public String getName() {
        return name;
    }

    public BigDecimal getCost() {
        return cost;
    }
}
