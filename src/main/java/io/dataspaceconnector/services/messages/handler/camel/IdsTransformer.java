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
package io.dataspaceconnector.services.messages.handler.camel;

import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.ContractAgreementMessageImpl;
import de.fraunhofer.iais.eis.ContractRequest;
import de.fraunhofer.iais.eis.ContractRequestMessageImpl;
import de.fraunhofer.iais.eis.Resource;
import de.fraunhofer.iais.eis.ResourceUpdateMessageImpl;
import de.fraunhofer.isst.ids.framework.messaging.model.messages.MessagePayload;
import io.dataspaceconnector.exceptions.DeserializationException;
import io.dataspaceconnector.exceptions.MissingPayloadException;
import io.dataspaceconnector.services.ids.DeserializationService;
import io.dataspaceconnector.services.messages.handler.camel.dto.Request;
import io.dataspaceconnector.services.messages.handler.camel.dto.RouteMsg;
import io.dataspaceconnector.services.messages.handler.camel.dto.payload.ContractRuleListContainer;
import io.dataspaceconnector.services.messages.handler.camel.dto.payload.ContractTargetRuleMapContainer;
import io.dataspaceconnector.utils.ContractUtils;
import io.dataspaceconnector.utils.MessageUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

/**
 * Superclass for Camel processors that transform an incoming message's payload, e.g. by
 * deserialization.
 *
 * @param <I> the expected input type (body of the Camel {@link Exchange}).
 * @param <O> the output type (body of the Camel {@link Exchange} after transformation).
 */
public abstract class IdsTransformer<I, O> implements Processor {

    /**
     * Override of the the {@link Processor}'s process method. Calls the implementing class's
     * processInternal method and sets the result as the {@link Exchange}'s body.
     *
     * @param exchange the input.
     * @throws Exception if transformation fails.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void process(final Exchange exchange) throws Exception {
        exchange.getIn().setBody(processInternal((I) exchange.getIn().getBody(RouteMsg.class)));
    }

    /**
     * Transforms the input into the desired output type. To be implemented by sub classes.
     *
     * @param msg the incoming message.
     * @return the transformed input.
     * @throws Exception if transformation fails.
     */
    protected abstract O processInternal(I msg) throws Exception;
}

/**
 * Transforms the payload of a ContractRequestMessage from a {@link MessagePayload} to a
 * {@link ContractRequest}.
 */
@Component("ContractDeserializer")
@RequiredArgsConstructor
class ContractRequestTransformer extends IdsTransformer<
        RouteMsg<ContractRequestMessageImpl, MessagePayload>,
        RouteMsg<ContractRequestMessageImpl, ContractRequest>> {

    /**
     * Service for ids deserialization.
     */
    private final @NonNull DeserializationService deserializationService;

    /**
     * Deserializes the payload of a ContractRequestMessage to a ContractRequest.
     *
     * @param msg the incoming message.
     * @return a RouteMsg object with the initial header and the ContractRequest as payload.
     * @throws Exception if the payload cannot be deserialized.
     */
    @Override
    protected RouteMsg<ContractRequestMessageImpl, ContractRequest> processInternal(
            final RouteMsg<ContractRequestMessageImpl, MessagePayload> msg) throws Exception {
        final var contract = deserializationService
                .getContractRequest(MessageUtils.getPayloadAsString(msg.getBody()));
        return new Request<>(msg.getHeader(), contract);
    }

}

/**
 * Transforms the payload of a ResourceUpdateMessage from a {@link MessagePayload} to a
 * {@link Resource}.
 */
@Component("ResourceDeserializer")
@RequiredArgsConstructor
class ResourceTransformer extends IdsTransformer<
        RouteMsg<ResourceUpdateMessageImpl, MessagePayload>,
        RouteMsg<ResourceUpdateMessageImpl, Resource>> {

    /**
     * Service for ids deserialization.
     */
    private final @NonNull DeserializationService deserializationService;

    /**
     * Deserializes the payload of a ResourceUpdateMessage to a Resource.
     *
     * @param msg the incoming message.
     * @return a RouteMsg object with the initial header and the Resource as payload.
     * @throws Exception if the payload cannot be read or deserialized.
     */
    @Override
    protected RouteMsg<ResourceUpdateMessageImpl, Resource> processInternal(
            final RouteMsg<ResourceUpdateMessageImpl, MessagePayload> msg) throws Exception {
        final var payloadString = MessageUtils.getStreamAsString(msg.getBody());
        if (payloadString.isBlank()) {
            throw new MissingPayloadException("Payload is missing from ResourceUpdateMessage.");
        }

        final Resource resource;
        try {
            resource = deserializationService.getResource(payloadString);
        } catch (IllegalArgumentException e) {
            throw new DeserializationException("Deserialization failed.", e);
        }

        return new Request<>(msg.getHeader(), resource);
    }

}

/**
 * Transforms the payload of a ContractAgreementMessage from a {@link MessagePayload} to a
 * {@link ContractAgreement}.
 */
@Component("AgreementDeserializer")
@RequiredArgsConstructor
class ContractAgreementTransformer extends IdsTransformer<
        RouteMsg<ContractAgreementMessageImpl, MessagePayload>,
        RouteMsg<ContractAgreementMessageImpl, ContractAgreement>> {

    /**
     * Service for ids deserialization.
     */
    private final @NonNull DeserializationService deserializationService;

    /**
     * Deserializes the payload of a ContractAgreementMessage to a ContractAgreement.
     *
     * @param msg the incoming message.
     * @return a RouteMsg object with the initial header and the ContractAgreement as payload.
     * @throws Exception if the payload cannot be deserialized.
     */
    @Override
    protected RouteMsg<ContractAgreementMessageImpl, ContractAgreement> processInternal(
            final RouteMsg<ContractAgreementMessageImpl, MessagePayload> msg) throws Exception {
        final var agreement = deserializationService
                .getContractAgreement(MessageUtils.getPayloadAsString(msg.getBody()));
        return new Request<>(msg.getHeader(), agreement);
    }

}

/**
 * Transforms the payload of a contract request from a ContractRequest object to a container object
 * for the ContractRequest and the list of rules it contains.
 */
@Component("ContractRuleListTransformer")
class ContractRuleListTransformer extends IdsTransformer<
        RouteMsg<ContractRequestMessageImpl, ContractRequest>,
        RouteMsg<ContractRequestMessageImpl, ContractRuleListContainer>> {

    /**
     * Transforms the payload of the incoming RouteMsg from a ContractRequest to a container object
     * for the ContractRequest and the list of rules it contains.
     *
     * @param msg the incoming message.
     * @return a RouteMsg object with the initial header and the container object as payload.
     * @throws Exception if the contract request is null.
     */
    @Override
    protected RouteMsg<ContractRequestMessageImpl, ContractRuleListContainer> processInternal(
            final RouteMsg<ContractRequestMessageImpl, ContractRequest> msg) throws Exception {
        final var request = msg.getBody();
        final var rules = ContractUtils.extractRulesFromContract(request);
        return new Request<>(msg.getHeader(), new ContractRuleListContainer(request, rules));
    }

}

/**
 * Transforms the payload of a contract request from a container containing a ContractRequest and
 * its list of rules to a container containing the ContractRequest and its target-rule-map, that
 * links rules to their target artifact.
 */
@Component("ContractTargetRuleMapTransformer")
class ContractTargetRuleMapTransformer extends IdsTransformer<
        RouteMsg<ContractRequestMessageImpl, ContractRuleListContainer>,
        RouteMsg<ContractRequestMessageImpl, ContractTargetRuleMapContainer>> {

    /**
     *
     * @param msg the incoming message.
     * @return a RouteMsg object with the initial header and the new container object as payload.
     * @throws Exception
     */
    @Override
    protected RouteMsg<ContractRequestMessageImpl, ContractTargetRuleMapContainer> processInternal(
            final RouteMsg<ContractRequestMessageImpl, ContractRuleListContainer> msg)
            throws Exception {
        final var targetRuleMap = ContractUtils
                .getTargetRuleMap(msg.getBody().getRules());
        final var container = new ContractTargetRuleMapContainer(msg.getBody().getContractRequest(),
                targetRuleMap);
        return new Request<>(msg.getHeader(), container);
    }

}
