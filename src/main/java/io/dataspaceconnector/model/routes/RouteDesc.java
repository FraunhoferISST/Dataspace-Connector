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
package io.dataspaceconnector.model.routes;

import java.net.URI;

import io.dataspaceconnector.model.DeployMethod;
import io.dataspaceconnector.model.base.Description;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Describing route's properties.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RouteDesc extends Description {

    /**
     * The deploy method of the route.
     */
    private DeployMethod method;

    /**
     * The route configuration.
     */
    private String config;

    private URI start;

    private URI end;
}
