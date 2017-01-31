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
package com.github.codeframes.hal.tooling.api.doc.example.resources;

import com.github.codeframes.hal.tooling.api.doc.example.representations.Item;
import com.github.codeframes.hal.tooling.api.doc.example.representations.Order;
import com.github.codeframes.hal.tooling.api.doc.example.representations.OrderRepresentation;
import com.github.codeframes.hal.tooling.api.doc.example.representations.OrdersRepresentation;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/orders/")
public class OrderResource {

    /**
     * Retrieves all the orders
     *
     * @return
     */
    @GET
    public Response get() {

        List<Item> items = new ArrayList<>();
        items.add(new Item("pizza", 1));
        OrderRepresentation order = new OrderRepresentation("123", items, new BigDecimal("2.45"));

        OrdersRepresentation orders = new OrdersRepresentation(Collections.singletonList(order));

        return Response.ok(orders).type("application/hal+json").build();
    }

    /**
     * Retrieves an order for the given order_id
     *
     * @param orderId
     * @return
     */
    @GET
    @Path("/{order_id}")
    @Produces("application/hal+json")
    public Response getOrder(@PathParam("order_id") @Pattern(regexp = "\\d{3}") String orderId) {

        List<Item> items = new ArrayList<>();
        items.add(new Item("pizza", 1));
        OrderRepresentation order = new OrderRepresentation(orderId, items, new BigDecimal("2.45"));

        return Response.ok(order).build();
    }

    /**
     * Creates a new order resource
     *
     * @param order
     * @return
     */
    @POST
    @Consumes("application/json")
    @Produces("application/hal+json")
    public Response createOrder(@Valid OrderRepresentation order) {

        String orderId = "123";
        OrderRepresentation newOrder = new OrderRepresentation(orderId, order.getItems(), order.getCost());

        return Response.ok(newOrder).status(Response.Status.CREATED).build();
    }

    /**
     * Removes the order for the given order_id
     *
     * @param orderId the order id
     * @return
     */
    @DELETE
    @Path("/{order_id}")
    @Produces("application/json")
    public Response removeOrder(@PathParam("order_id") String orderId) {

        List<Item> items = new ArrayList<>();
        items.add(new Item("pizza", 1));

        Order order = new Order(items, new BigDecimal("2.45"));

        return Response.ok(order).build();
    }
}
