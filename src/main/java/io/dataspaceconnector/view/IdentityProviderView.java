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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.dataspaceconnector.model.base.RegistrationStatus;
import io.dataspaceconnector.view.util.ViewConstants;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.net.URI;
import java.time.ZonedDateTime;

/**
 * A DTO for controlled exposing of identity provider information in API responses.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class IdentityProviderView extends RepresentationModel<IdentityProviderView> {

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
     * The title of the identity provider.
     */
    private String title;

    /**
     * The description of the identity provider.
     */
    private String description;

    /**
     * The access url of the identity provider.
     */
    private URI location;

    /**
     * The registration status.
     */
    private RegistrationStatus status;
}
