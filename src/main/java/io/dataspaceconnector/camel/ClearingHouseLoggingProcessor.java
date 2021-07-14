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
package io.dataspaceconnector.camel;
import de.fraunhofer.iais.eis.Message;
import io.dataspaceconnector.camel.dto.Request;
import io.dataspaceconnector.camel.dto.Response;
import io.dataspaceconnector.camel.dto.RouteMsg;
import io.dataspaceconnector.config.ConnectorConfiguration;
import io.dataspaceconnector.exception.PolicyExecutionException;
import io.dataspaceconnector.service.message.type.LogMessageService;
import io.dataspaceconnector.util.UUIDUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import java.net.URI;

/**
 * Logs IDS messages in the Clearing House.
 */
@Component("ClearingHouseLoggingProcessor")
@Log4j2
@RequiredArgsConstructor
public class ClearingHouseLoggingProcessor implements Processor {
    /**
     * Service for ids log messages.
     */
    private final @NonNull LogMessageService logMessageService;
    /**
     * Service for configuring policy settings.
     */
    private final @NonNull ConnectorConfiguration connectorConfig;

    /**
     * Processes the input. Extract the request/response IDS message,
     * create a LogMessage with the IDS message as payload, then send to the Clearing House.
     *
     * @param exchange the input.
     * @throws Exception if an error occurs.
     */
    @Override
    public void process(final Exchange exchange) throws Exception {
        RouteMsg msg = exchange.getIn().getBody(Request.class);
        if (msg == null) {
            msg = exchange.getIn().getBody(Response.class);
        }
        var idsMessageHeader = (Message) msg.getHeader();
        logIDSMessage(idsMessageHeader);
    }

    /**
     *
     * Creates a LogMessage with the IDS message as payload, then sends to the Clearing House.
     *
     * @param idsMessageHeader the input.
     */
    public void logIDSMessage(final Message idsMessageHeader) {

        final var transferContractID = UUIDUtils
                .uuidFromUri(idsMessageHeader.getTransferContract());
        final var clearingHouse = connectorConfig.getClearingHouse();
        if (!clearingHouse.equals(URI.create(""))) {
            try {
                logMessageService.sendMessage(
                        URI.create(clearingHouse + transferContractID.toString()),
                        idsMessageHeader.toRdf());

            } catch (PolicyExecutionException e) {
                if (log.isWarnEnabled()) {
                    log.warn("Unable to send log message");
                }
            }
        }
    }
}
