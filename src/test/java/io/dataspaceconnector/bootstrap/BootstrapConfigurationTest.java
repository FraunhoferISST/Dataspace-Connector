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
package io.dataspaceconnector.bootstrap;

import io.dataspaceconnector.services.messages.GlobalMessageService;
import io.dataspaceconnector.services.resources.CatalogService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;

@SpringBootTest
public class BootstrapConfigurationTest {

    @MockBean
    private GlobalMessageService messageService;

    @Autowired
    CatalogService catalogService;

    @Autowired
    BootstrapConfiguration configuration;

    @BeforeEach
    public void prepare() {
        catalogService.getAll(Pageable.unpaged()).forEach( catalog -> catalogService.delete(catalog.getId()));
    }

//    @SneakyThrows
//    @Test
//    public void bootstrap_files_registerCatalogs() {
//        /* ARRANGE */
//        Mockito.doReturn(true).when(configuration).registerAtBroker(Mockito.any(), Mockito.any());
//
//        /* ACT */
//        configuration.bootstrap();
//
//        /* ASSERT */
//        assertEquals(2, catalogService.getAll(Pageable.unpaged()).getSize());
//    }
}
