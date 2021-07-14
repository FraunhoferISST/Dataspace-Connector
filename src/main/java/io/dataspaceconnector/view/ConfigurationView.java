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
package io.dataspaceconnector.view;

import java.net.URI;
import java.time.ZonedDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.dataspaceconnector.model.configuration.DeployMode;
import io.dataspaceconnector.model.configuration.LogLevel;
import io.dataspaceconnector.model.configuration.SecurityProfile;
import io.dataspaceconnector.view.util.ViewConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

/**
 * A DTO for controlled exposing of configuration information in API responses.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "configurations", itemRelation = "configuration")
public class ConfigurationView extends RepresentationModel<ConfigurationView> {

    /**
     * The creation date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ViewConstants.DATE_TIME_FORMAT)
    private ZonedDateTime creationDate;

    /**
     * The last modification date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ViewConstants.DATE_TIME_FORMAT)
    private ZonedDateTime modificationDate;

    /**
     * The title of the configuration.
     */
    private String title;

    /**
     * The description of the configuration.
     */
    private String description;

    /**
     * The access url of the connector.
     */
    private URI connectorEndpoint;

    /**
     * The project version.
     */
    private String version;

    /**
     * The curator.
     */
    private URI curator;

    /**
     * The maintainer.
     */
    private URI maintainer;

    /**
     * The list of inbound model version.
     */
    private List<String> inboundModelVersion;

    /**
     * The outbound model version.
     */
    private String outboundModelVersion;

    /**
     * The security profile.
     */
    private SecurityProfile securityProfile;

    /**
     * The log level.
     */
    private LogLevel logLevel;

    /**
     * The deploy mode of the connector.
     */
    private DeployMode deployMode;

    /**
     * The proxy configuration.
     */
    private ProxyView proxy;

    /**
     * The trust store.
     */
    private TruststoreView trustStore;

    /**
     * The key store.
     */
    private KeystoreView keyStore;
}
