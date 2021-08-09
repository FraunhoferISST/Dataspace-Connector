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
package io.dataspaceconnector.service.resource.type;

import de.fraunhofer.ids.messaging.core.config.ConfigProducer;
import de.fraunhofer.ids.messaging.core.config.ConfigUpdateException;
import io.dataspaceconnector.model.configuration.Configuration;
import io.dataspaceconnector.model.configuration.ConfigurationDesc;
import io.dataspaceconnector.repository.ConfigurationRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ConfigurationServiceTest {

    @SpyBean
    private ConfigurationRepository repo;

    @SpyBean
    private ConfigProducer configProducer;

    @SpyBean
    private ConfigurationService service;

    @Test
    public void swapActiveConfig_hasActiveConfig_willSetPassedConfigAsActiveAndTheOldOneAsInActive()
            throws ConfigUpdateException {
        /* ARRANGE */
        final var config = service.create(new ConfigurationDesc());

        /* ACT */
        service.swapActiveConfig(config.getId());

        /* ASSERT */
        assertEquals(config.getId(), service.getActiveConfig().getId());
    }

    @Test
    public void update_validInput_returnConfiguration() {
        /* ARRANGE */
        final String title = "Title";
        final var config = new Configuration();
        final var desc = new ConfigurationDesc();
        desc.setTitle(title);
        final var entityId = UUID.randomUUID();

        Mockito.doReturn(config).when(service).update(entityId, desc);

        /* ACT */
        final var result = service.update(entityId, desc);

        /* ASSERT */
        assertNotNull(result);
    }
}
