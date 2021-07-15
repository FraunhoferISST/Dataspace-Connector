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
package io.dataspaceconnector.model.endpoint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * The endpoint factory proxy class.
 */
@Component
public class EndpointFactoryProxy extends EndpointFactory<Endpoint, EndpointDesc> {

    /**
     * The factory for the app endpoint.
     */
    @Autowired
    private AppEndpointFactory apps;

    /**
     * The factory for the connector endpoint.
     */
    @Autowired
    private ConnectorEndpointFactory connector;

    /**
     * The factory for the generic endpoint.
     */
    @Autowired
    private GenericEndpointFactory generic;

    @Override
    protected final Endpoint initializeEntity(final EndpointDesc desc) {
        if (desc instanceof AppEndpointDesc) {
            return apps.initializeEntity((AppEndpointDesc) desc);
        } else if (desc instanceof ConnectorEndpointDesc) {
            return connector.initializeEntity((ConnectorEndpointDesc) desc);
        }

        assert desc instanceof GenericEndpointDesc;
        return generic.initializeEntity((GenericEndpointDesc) desc);
    }

    @Override
    protected final boolean updateInternal(final Endpoint endpoint, final EndpointDesc desc) {
        if (endpoint instanceof AppEndpoint && desc instanceof AppEndpointDesc) {
            return apps.updateInternal((AppEndpoint) endpoint, (AppEndpointDesc) desc);
        } else if (endpoint instanceof ConnectorEndpoint && desc instanceof ConnectorEndpointDesc) {
            return connector.updateInternal((ConnectorEndpoint) endpoint,
                                            (ConnectorEndpointDesc) desc);
        }

        assert endpoint instanceof GenericEndpoint && desc instanceof GenericEndpointDesc;
        return generic.updateInternal((GenericEndpoint) endpoint, (GenericEndpointDesc) desc);
    }
}