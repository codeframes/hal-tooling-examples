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
package com.github.codeframes.hal.tooling.api.doc.example.providers;

import com.github.codeframes.hal.tooling.core.HalRepresentable;
import com.github.codeframes.hal.tooling.core.Link;
import com.github.codeframes.hal.tooling.json.representations.VndError;
import com.github.codeframes.hal.tooling.json.representations.VndErrors;

import javax.annotation.Priority;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.Priorities;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Provider
@Priority(Priorities.USER)
public class ConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<VndError> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {

            VndError error = new VndError.Builder()
                    .aboutLink(new Link("about", "http://path.to/user/resource/1"))
                    .describesLink(new Link("describes", "http://path.to/describes"))
                    .helpLink(new Link("help", "http://path.to/help"))
                    .message(getFieldName(violation) + " " + violation.getMessage())
                    .logref(UUID.randomUUID().toString())
                    .build();

            errors.add(error);
        }

        final HalRepresentable vndError;
        if (errors.size() == 1) {
            vndError = errors.get(0);
        } else {
            vndError = new VndErrors(errors);
        }

        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(vndError)
                .type("application/vnd.error+json")
                .build();
    }

    private static String getFieldName(ConstraintViolation<?> violation) {
        Iterator<Path.Node> itr = violation.getPropertyPath().iterator();
        Path.Node node = itr.next();
        while (itr.hasNext()) {
            node = itr.next();
        }
        return node.getName();
    }
}
