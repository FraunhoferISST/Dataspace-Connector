package io.dataspaceconnector.service.message.handler.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.fraunhofer.iais.eis.ArtifactRequestMessageImpl;
import de.fraunhofer.ids.messaging.handler.message.MessagePayload;
import io.dataspaceconnector.common.MessageUtils;
import io.dataspaceconnector.common.QueryInput;
import io.dataspaceconnector.exception.InvalidInputException;
import io.dataspaceconnector.model.message.ArtifactResponseMessageDesc;
import io.dataspaceconnector.service.EntityResolver;
import io.dataspaceconnector.service.message.handler.dto.Response;
import io.dataspaceconnector.service.message.handler.dto.RouteMsg;
import io.dataspaceconnector.service.message.type.ArtifactResponseService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

/**
 * Fetches the data of an artifact as the response to an ArtifactRequestMessage.
 */
@Log4j2
@Component("DataRequestProcessor")
@RequiredArgsConstructor
class DataRequestProcessor extends IdsProcessor<
        RouteMsg<ArtifactRequestMessageImpl, MessagePayload>> {

    /**
     * Service for handling artifact response messages.
     */
    private final @NonNull ArtifactResponseService messageService;

    /**
     * Service for resolving entities.
     */
    private final @NonNull EntityResolver entityResolver;

    /**
     * Fetches the data of the requested artifact as the response payload and creates an
     * ArtifactResponseMessage as the response header.
     *
     * @param msg the incoming message.
     * @return a Response object with an ArtifactResponseMessage as header and the data as payload.
     * @throws Exception if the {@link QueryInput} given in the request's payload is invalid or
     *                   there is an error fetching the data or an error occurs building the
     *                   response.
     */
    @Override
    protected Response processInternal(final RouteMsg<ArtifactRequestMessageImpl,
            MessagePayload> msg) throws Exception {
        final var artifact = MessageUtils.extractRequestedArtifact(msg.getHeader());
        final var issuer = MessageUtils.extractIssuerConnector(msg.getHeader());
        final var messageId = MessageUtils.extractMessageId(msg.getHeader());
        final var transferContract = MessageUtils.extractTransferContract(msg.getHeader());

        final var queryInput = getQueryInputFromPayload(msg.getBody());
        final var data = entityResolver.getDataByArtifactId(artifact, queryInput);

        final var desc = new ArtifactResponseMessageDesc(issuer, messageId, transferContract);
        final var responseHeader = messageService.buildMessage(desc);

        return new Response(responseHeader, Base64Utils.encodeToString(data.readAllBytes()));
    }

    /**
     * Read query parameters from message payload.
     *
     * @param messagePayload The message's payload.
     * @return the query input.
     * @throws InvalidInputException If the query input is not empty but invalid.
     */
    private QueryInput getQueryInputFromPayload(final MessagePayload messagePayload)
            throws InvalidInputException {
        try {
            final var payload = MessageUtils.getStreamAsString(messagePayload);
            if (payload.equals("") || payload.equals("null")) {
                // Query input is optional, so no rejection message will be sent. Query input will
                // be checked for null value in HttpService.class.
                return null;
            } else {
                return new ObjectMapper().readValue(payload, QueryInput.class);
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("Invalid query input. [exception=({})]", e.getMessage(), e);
            }
            throw new InvalidInputException("Invalid query input.", e);
        }
    }

}