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
package io.dataspaceconnector.model.truststore;

import io.dataspaceconnector.model.base.AbstractFactory;
import io.dataspaceconnector.utils.MetadataUtils;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
public class TruststoreFactory extends AbstractFactory<Truststore, TruststoreDesc> {

    private static final String DEFAULT_PASSWORD = "";

    private static final URI DEFAULT_NAME = URI.create("");

    @Override
    protected Truststore initializeEntity(final TruststoreDesc desc) {
        return new Truststore();
    }

    @Override
    public boolean updateInternal(final Truststore truststore, final TruststoreDesc desc) {
        final var hasUpdatedName = updateName(truststore, desc.getName());
        final var hasUpdatedPassword = updatePassword(truststore, desc.getPassword());

        return hasUpdatedName || hasUpdatedPassword;
    }

    private boolean updatePassword(final Truststore truststore, final String password) {
        final var newPassword = MetadataUtils.updateString(truststore.getPassword(),
                password, DEFAULT_PASSWORD);
        newPassword.ifPresent(truststore::setPassword);

        return newPassword.isPresent();
    }

    private boolean updateName(final Truststore truststore, final URI name) {
        final var newLocation =
                MetadataUtils.updateUri(truststore.getName(), name, DEFAULT_NAME);
        newLocation.ifPresent(truststore::setName);

        return newLocation.isPresent();
    }
}
