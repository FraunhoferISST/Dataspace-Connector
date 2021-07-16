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
package io.dataspaceconnector.model.route;

import io.dataspaceconnector.model.named.NamedDescription;
import io.dataspaceconnector.model.configuration.DeployMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Describing route's properties.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RouteDesc extends NamedDescription {

    /**
     * The route configuration.
     */
    private String configuration;

    /**
     * The deploy method of the route.
     */
    private DeployMethod deploy = DeployMethod.NONE;
}
