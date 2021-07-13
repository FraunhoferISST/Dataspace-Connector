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

import com.fasterxml.jackson.annotation.JsonFormat;
import io.dataspaceconnector.model.base.RegistrationStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * A DTO for controlled exposing of clearing house information in API responses.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class ClearingHouseView extends RepresentationModel<ClearingHouseView> {

    /**
     * The creation date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime creationDate;

    /**
     * The last modification date.
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime modificationDate;

    /**
     * The access url of the clearing house.
     */
    private URI location;

    /**
     * The title of the clearing house.
     */
    private String title;

    /**
     * The description of the clearing house.
     */
    private String description;

    /**
     * The status of registration.
     */
    private RegistrationStatus registrationStatus;
}