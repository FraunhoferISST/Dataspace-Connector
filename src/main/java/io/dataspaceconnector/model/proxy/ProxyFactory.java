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
package io.dataspaceconnector.model.proxy;

import java.net.URI;
import java.util.ArrayList;

import io.dataspaceconnector.model.auth.Authentication;
import io.dataspaceconnector.model.base.AbstractFactory;
import io.dataspaceconnector.utils.MetadataUtils;
import org.springframework.stereotype.Component;

@Component
public class ProxyFactory extends AbstractFactory<Proxy, ProxyDesc> {

    private static final URI DEFAULT_LOCATION = URI.create("");

    @Override
    protected Proxy initializeEntity(final ProxyDesc desc) {
        final var proxy = new Proxy();
        proxy.setExclusions(new ArrayList<>());

        return proxy;
    }

    @Override
    public boolean updateInternal(final Proxy proxy, final ProxyDesc desc){
        final var hasUpdatedLocation = updateLocation(proxy, desc.getLocation());
        final var hasUpdatedAuthentication = updateAuthentication(proxy, desc.getAuthentication());

        return hasUpdatedLocation || hasUpdatedAuthentication;
    }

    private boolean updateAuthentication(final Proxy proxy, final Authentication authentication) {
        if(proxy.getAuthentication() == null && authentication == null){
            return false;
        }

        if(proxy.getAuthentication() !=null && authentication == null){
            proxy.setAuthentication(null);
            return true;
        }

        proxy.setAuthentication(authentication);
        return true;
    }

    private boolean updateLocation(final Proxy proxy, final URI location) {
        final var newLocation =
                MetadataUtils.updateUri(proxy.getLocation(), location, DEFAULT_LOCATION);
        newLocation.ifPresent(proxy::setLocation);

        return newLocation.isPresent();
    }

}
