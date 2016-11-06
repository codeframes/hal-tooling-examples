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
package com.github.codeframes.hal.tooling.example.resources;

import com.github.codeframes.hal.tooling.core.Curie;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.example.representations.RootRepresentation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Path("/")
public class RootResource {

    @GET
    @Produces("application/hal+json;charset=UTF-8")
    public Response get() {

        List<Curie> curies = Collections.singletonList(new Curie("itm", "http://example.com/docs/items/{rel}"));

        Link itemsLink = new Link("itm:items", "/items/");
        Link itemLink = new Link("itm:item", "/items/{item_id}");
        Link createItemLink = new Link("itm:create-item", "/items/");

        return Response.ok(new RootRepresentation(curies, itemsLink, itemLink, createItemLink)).build();
    }
}
