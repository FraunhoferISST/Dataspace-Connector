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
package io.dataspaceconnector.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {ControllerUtils.class})
class ControllerUtilsTest {

    private final Exception exception = new Exception("Some exception.");

    @Test
    public void respondIdsMessageFailed_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var msg = ErrorMessages.MESSAGE_HANDLING_FAILED.toString();
        final var expectedResponse = new ResponseEntity<>(msg, HttpStatus.INTERNAL_SERVER_ERROR);

        /* ACT */
        final var response = ControllerUtils.respondIdsMessageFailed(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondReceivedInvalidResponse_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var msg = ErrorMessages.INVALID_MESSAGE;
        final var expectedResponse = new ResponseEntity<>(msg, HttpStatus.BAD_GATEWAY);

        /* ACT */
        final var response = ControllerUtils.respondReceivedInvalidResponse(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(502, response.getStatusCodeValue());
    }

    @Test
    public void respondConfigurationUpdateError_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var expectedResponse = new ResponseEntity<>("Failed to update configuration.",
                HttpStatus.INTERNAL_SERVER_ERROR);

        /* ACT */
        final var response = ControllerUtils.respondConfigurationUpdateError(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondDeserializationError_validUri_returnValidResponseEntity() {
        /* ARRANGE */
        final var resourceId = URI.create("https://requestedResource");
        final var expectedResponse = new ResponseEntity<>("Resource not found.",
                HttpStatus.NOT_FOUND);

        /* ACT */
        final var response = ControllerUtils.respondResourceNotFound(resourceId);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondPatternNotIdentified_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var expectedResponse = new ResponseEntity<>("Could not identify pattern.",
                HttpStatus.BAD_REQUEST);

        /* ACT */
        final var response = ControllerUtils.respondPatternNotIdentified(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondInvalidInput_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var expectedResponse = new ResponseEntity<>("Invalid input, processing failed. "
                + exception.getMessage(), HttpStatus.BAD_REQUEST);

        /* ACT */
        final var response = ControllerUtils.respondInvalidInput(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondFailedToBuildContractRequest_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var expectedResponse = new ResponseEntity<>("Failed to build contract request.",
                HttpStatus.INTERNAL_SERVER_ERROR);

        /* ACT */
        final var response = ControllerUtils.respondFailedToBuildContractRequest(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondFailedToStoreEntity_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var expectedResponse = new ResponseEntity<>("Failed to store entity. "
                + exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        /* ACT */
        final var response = ControllerUtils.respondFailedToStoreEntity(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondConnectionTimedOut_validException_returnValidResponseEntity() {
        /* ARRANGE */
        final var expectedResponse = new ResponseEntity<>(ErrorMessages.GATEWAY_TIMEOUT.toString(),
                HttpStatus.GATEWAY_TIMEOUT);

        /* ACT */
        final var response = ControllerUtils.respondConnectionTimedOut(exception);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }

    @Test
    public void respondReceivedInvalidResponse_null_returnValidResponseEntity() {
        /* ARRANGE */
        final var msg = ErrorMessages.INVALID_MESSAGE;
        final var expectedResponse = new ResponseEntity<>(msg, HttpStatus.BAD_GATEWAY);

        /* ACT */
        final var response = ControllerUtils.respondReceivedInvalidResponse();

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(502, response.getStatusCodeValue());
    }

    @Test
    public void respondWithMessageContent_validMap_returnValidResponseEntity() {
        /* ARRANGE */
        final var obj = new Object();
        final var map = Map.of("header", obj);
        final var expectedResponse = new ResponseEntity<>(map, HttpStatus.EXPECTATION_FAILED);

        /* ACT */
        final var response = ControllerUtils.respondWithContent(map);

        /* ARRANGE */
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(expectedResponse, response);
    }
}
