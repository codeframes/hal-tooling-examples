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
import com.github.codeframes.hal.tooling.example.representations.ItemRepresentation;
import com.github.codeframes.hal.tooling.example.representations.ItemsRepresentation;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Path("/items/")
public class ItemResource {

    @GET
    @Produces("application/hal+json;charset=UTF-8")
    public Response getItems() {

        List<Link> links = Arrays.asList(new Link("self", "/items/321"), new Link("itm:remove-item", "/items/321"));
        Link imageLink = new Link("itm:image", "/images/001");

        ItemRepresentation item = new ItemRepresentation(links, Collections.singletonList(imageLink), "cpu", new BigDecimal("959.95"));

        List<Curie> curies = Collections.singletonList(new Curie("itm", "http://example.com/docs/items/{rel}"));
        Link selfLink = new Link("self", "/items/");

        ItemsRepresentation items = new ItemsRepresentation(curies, selfLink, Collections.singletonList(item));

        return Response.ok(items).build();
    }

    @GET
    @Path("/{item_id}")
    @Produces("application/hal+json;charset=UTF-8")
    public Response getItem(@PathParam("item_id") String itemId) {

        List<Curie> curies = Collections.singletonList(new Curie("itm", "http://example.com/docs/items/{rel}"));

        List<Link> links = Arrays.asList(new Link("self", "/items/" + itemId), new Link("itm:remove-item", "/items/" + itemId));
        Link imageLink = new Link("itm:image", "/images/001");

        ItemRepresentation item = new ItemRepresentation(curies, links, Collections.singletonList(imageLink), "cpu", new BigDecimal("959.95"));

        return Response.ok(item).build();
    }

    @POST
    @Consumes("application/json;charset=UTF-8")
    @Produces("application/hal+json;charset=UTF-8")
    public Response createItem(@Valid ItemRepresentation item) {

        String itemId = "654";

        List<Curie> curies = Collections.singletonList(new Curie("itm", "http://example.com/docs/items/{rel}"));

        List<Link> links = Arrays.asList(new Link("self", "/items/" + itemId), new Link("itm:remove-item", "/items/" + itemId));
        Link imageLink = new Link("itm:image", "/images/001");

        ItemRepresentation newItem = new ItemRepresentation(curies, links, Collections.singletonList(imageLink), item.getName(), item.getCost());

        return Response.ok(newItem).status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{item_id}")
    @Produces("application/json;charset=UTF-8")
    public Response removeOrder(@PathParam("item_id") String itemId) {

        ItemRepresentation item = new ItemRepresentation("cpu", new BigDecimal("959.95"));

        return Response.ok(item).build();
    }
}
