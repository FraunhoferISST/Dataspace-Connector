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
package io.dataspaceconnector.exception;

import io.dataspaceconnector.util.ErrorMessages;

/**
 * Thrown to indicate that a problem with a message request occurred.
 */
public class MessageRequestException extends RuntimeException {
    /**
     * Default serial version uid.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Construct a MessageRequestException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public MessageRequestException(final ErrorMessages msg) {
        super(msg.toString());
    }

    /**
     * Construct a MessageRequestException with the specified detail message.
     *
     * @param msg The detail message.
     */
    public MessageRequestException(final String msg) {
        super(msg);
    }

    /**
     * Construct a MessageRequestException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public MessageRequestException(final ErrorMessages msg, final Throwable cause) {
        super(msg.toString(), cause);
    }

    /**
     * Construct a MessageRequestException with the specified detail message and cause.
     *
     * @param msg   The detail message.
     * @param cause The cause.
     */
    public MessageRequestException(final String msg, final Throwable cause) {
        super(msg, cause);
    }
}
