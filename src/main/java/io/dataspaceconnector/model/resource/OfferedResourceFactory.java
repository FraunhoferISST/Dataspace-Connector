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
package io.dataspaceconnector.model.resource;

import io.dataspaceconnector.common.exception.InvalidEntityException;
import io.dataspaceconnector.common.net.EndpointUtils;
import io.dataspaceconnector.common.util.ValidationUtils;
import io.dataspaceconnector.service.resource.type.OfferedResourceService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.List;
import java.util.UUID;

/**
 * Creates and updates a resource.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public final class OfferedResourceFactory extends ResourceFactory<OfferedResource,
        OfferedResourceDesc> {

    /**
     * The service for resource handling.
     */
    private final @NonNull OfferedResourceService resourceService;

    @Override
    protected OfferedResource createInternal(final OfferedResourceDesc desc) {
        return new OfferedResource();
    }

    /**
     * Check if uri is a valid id of an existing resource.
     *
     * @param resource The resource passed to the factory.
     * @param samples  The new samples.
     */
    @Override
    protected void validateSamples(final Resource resource, final List<URI> samples) {
        for (final var sample : samples) {
            if (ValidationUtils.isInvalidUri(sample.toString())) {
                throw new InvalidEntityException("Sample is not a valid uri.");
            }

            UUID resourceId;
            try {
                resourceId = EndpointUtils.getUUIDFromPath(sample);
            } catch (Exception exception) {
                throw new InvalidEntityException("Sample is not a valid uri.");
            }

            if (!resourceService.doesExist(resourceId)) {
                if (log.isWarnEnabled()) {
                    log.warn("Could not find matching resource. [id=({})]", sample);
                }
                throw new InvalidEntityException("Could not find matching resource.");
            }
        }
    }
}
