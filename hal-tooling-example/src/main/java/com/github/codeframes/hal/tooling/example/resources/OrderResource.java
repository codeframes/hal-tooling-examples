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

import com.github.codeframes.hal.tooling.example.representations.OrderItem;
import com.github.codeframes.hal.tooling.example.representations.OrderRepresentation;
import com.github.codeframes.hal.tooling.example.representations.OrdersRepresentation;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/orders/")
public class OrderResource {

    @GET
    @Produces("application/hal+json;charset=UTF-8")
    public Response getOrders() {

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("ssd", 2));
        OrderRepresentation order = new OrderRepresentation("456", orderItems, new BigDecimal("167.64"));

        OrdersRepresentation orders = new OrdersRepresentation(Collections.singletonList(order));

        return Response.ok(orders).build();
    }

    @GET
    @Path("/{order_id:\\d+}")
    @Produces("application/hal+json;charset=UTF-8")
    public Response getOrder(@PathParam("order_id") String orderId) {

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("ssd", 2));
        OrderRepresentation order = new OrderRepresentation(orderId, orderItems, new BigDecimal("167.64"));

        return Response.ok(order).build();
    }

    @POST
    @Consumes("application/json")
    @Produces("application/hal+json;charset=UTF-8")
    public Response createOrder(@Valid OrderRepresentation order) {

        String orderId = "789";
        OrderRepresentation newOrder = new OrderRepresentation(orderId, order.getOrderItems(), order.getCost());

        return Response.ok(newOrder).status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("/{order_id:\\d+}")
    @Produces("application/json;charset=UTF-8")
    public Response removeOrder(@PathParam("order_id") String orderId) {

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem("ssd", 2));
        OrderRepresentation order = new OrderRepresentation(orderId, orderItems, new BigDecimal("167.64"));

        return Response.ok(order).build();
    }
}
