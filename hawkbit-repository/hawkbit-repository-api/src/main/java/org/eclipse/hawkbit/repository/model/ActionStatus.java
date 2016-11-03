/**
 * Copyright (c) 2015 Bosch Software Innovations GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.hawkbit.repository.model;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.eclipse.hawkbit.repository.model.Action.Status;

/**
 * Status information of an {@link Action} which can be provided by the
 * {@link Target} or is added by the update server itself. This can be the start
 * of the {@link Action} life cycle, the end and update notifications in
 * between.
 *
 */
public interface ActionStatus extends TenantAwareBaseEntity {

    /**
     * @return time in {@link TimeUnit#MILLISECONDS} when the status was
     *         reported.
     */
    Long getOccurredAt();

    /**
     * @param occurredAt
     *            time in {@link TimeUnit#MILLISECONDS} when the status was
     *            reported.
     */
    void setOccurredAt(Long occurredAt);

    /**
     * Adds message including splitting in case it exceeds 512 length.
     *
     * @param message
     *            to add
     */
    void addMessage(String message);

    /**
     * @return immutable list of message entries that in the
     *         {@link ActionStatus}.
     */
    List<String> getMessages();

    /**
     * @return {@link Action} this {@link ActionStatus} belongs to.
     */
    Action getAction();

    /**
     * @param action
     *            this {@link ActionStatus} belongs to.
     */
    void setAction(Action action);

    /**
     * @return the {@link Status} of this {@link ActionStatus}. Caused
     *         potentially a transition change of the {@link #getAction()} if
     *         different from the previous {@link ActionStatus#getStatus()}.
     */
    Status getStatus();

    /**
     * @param status
     *            of this {@link ActionStatus}. May cause a transition change of
     *            the {@link #getAction()} if different from the previous
     *            {@link ActionStatus#getStatus()}.
     */
    void setStatus(Status status);

}
