/*
 * Copyright 2020 Fraunhofer Institute for Software and Systems Engineering
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.dataspaceconnector.controller.configuration.exceptionhandler;

import io.configmanager.extensions.routes.camel.exceptions.RouteCreationException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RouteCreationExceptionHandlerTest {

    private RouteCreationExceptionHandler exceptionHandler = new RouteCreationExceptionHandler();

    @Test
    public void handleRouteCreationException_returnStatusInternalServerError() {
        /* ACT */
        final var response = exceptionHandler
                .handleRouteCreationException(
                        new RouteCreationException("Failed to create route."));

        /* ASSERT */
        assertNotNull(response);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        final var headers = response.getHeaders();
        assertEquals(MediaType.APPLICATION_JSON, headers.getContentType());

        final var body = response.getBody();
        assertEquals("Failed to create Camel route.", body.get("message"));
    }

}
