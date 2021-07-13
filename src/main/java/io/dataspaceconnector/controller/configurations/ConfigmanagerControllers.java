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
package io.dataspaceconnector.controller.configurations;

import io.dataspaceconnector.controller.resource.BaseResourceController;
import io.dataspaceconnector.model.clearinghouse.ClearingHouse;
import io.dataspaceconnector.model.clearinghouse.ClearingHouseDesc;
import io.dataspaceconnector.model.configuration.Configuration;
import io.dataspaceconnector.model.configuration.ConfigurationDesc;
import io.dataspaceconnector.model.datasource.DataSource;
import io.dataspaceconnector.model.datasource.DataSourceDesc;
import io.dataspaceconnector.model.identityprovider.IdentityProvider;
import io.dataspaceconnector.model.identityprovider.IdentityProviderDesc;
import io.dataspaceconnector.service.configuration.ClearingHouseService;
import io.dataspaceconnector.service.configuration.ConfigurationService;
import io.dataspaceconnector.service.configuration.DataSourceService;
import io.dataspaceconnector.service.configuration.IdentityProviderService;
import io.dataspaceconnector.view.ClearingHouseView;
import io.dataspaceconnector.view.ConfigurationView;
import io.dataspaceconnector.view.DataSourceView;
import io.dataspaceconnector.view.IdentityProviderView;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the Configuration Manager.
 */
public final class ConfigmanagerControllers {

    /**
     * Offers the endpoints for managing clearing houses.
     */
    @RestController
    @RequestMapping("/api/clearinghouses")
    @Tag(name = "Clearing House", description = "Endpoints for CRUD operations on clearing houses")
    public static class ClearingHouseController
            extends BaseResourceController<ClearingHouse, ClearingHouseDesc,
            ClearingHouseView, ClearingHouseService> { }

    /**
     * Offers the endpoints for managing configurations.
     */
    @RestController
    @RequestMapping("/api/configurations")
    @RequiredArgsConstructor
    @Tag(name = "Configuration", description = "Endpoints for CRUD operations on configurations")
    public static class ConfigurationController
            extends BaseResourceController<Configuration, ConfigurationDesc,
            ConfigurationView, ConfigurationService> { }

    /**
     * Offers the endpoints for managing data sources.
     */
    @RestController
    @RequestMapping("/api/datasources")
    @RequiredArgsConstructor
    @Tag(name = "Data Source", description = "Endpoints for CRUD operations on data sources")
    public static class DataSourceController
            extends BaseResourceController<DataSource, DataSourceDesc, DataSourceView,
            DataSourceService> { }

    /**
     * Offers the endpoints for managing identity provider endpoints.
     */
    @RestController
    @RequestMapping("/api/identityproviders")
    @Tag(name = "Identity Provider", description = "Endpoints for CRUD operations on"
            + " identity providers")
    public static class IdentityProviderController
            extends BaseResourceController<IdentityProvider, IdentityProviderDesc,
            IdentityProviderView, IdentityProviderService> {
    }
}