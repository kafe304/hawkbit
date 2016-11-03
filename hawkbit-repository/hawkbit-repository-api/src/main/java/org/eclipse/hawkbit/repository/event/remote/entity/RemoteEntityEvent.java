/**
 * Copyright (c) 2015 Bosch Software Innovations GmbH and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.eclipse.hawkbit.repository.event.remote.entity;

import org.apache.commons.lang3.ClassUtils;
import org.eclipse.hawkbit.repository.event.remote.EventEntityManagerHolder;
import org.eclipse.hawkbit.repository.event.remote.RemoteIdEvent;
import org.eclipse.hawkbit.repository.model.TenantAwareBaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * A base definition class for remote events which contain a tenant aware base
 * entity.
 *
 * @param <E>
 *            the type of the entity
 */
public class RemoteEntityEvent<E extends TenantAwareBaseEntity> extends RemoteIdEvent {

    private static final Logger LOG = LoggerFactory.getLogger(RemoteEntityEvent.class);

    private static final long serialVersionUID = 1L;

    private String entityClass;

    private transient E entity;

    /**
     * Default constructor.
     */
    protected RemoteEntityEvent() {
        // for serialization libs like jackson
    }

    /**
     * Constructor.
     *
     * @param baseEntity
     *            the base entity
     * @param applicationId
     *            the origin application id
     */
    protected RemoteEntityEvent(final E baseEntity, final String applicationId) {
        super(baseEntity.getId(), baseEntity.getTenant(), applicationId);
        this.entityClass = baseEntity.getClass().getName();
        this.entity = baseEntity;
    }

    /**
     * @return the entityClass
     */
    public String getEntityClass() {
        return entityClass;
    }

    @JsonIgnore
    public E getEntity() {
        if (entity == null) {
            entity = reloadEntityFromRepository();
        }
        return entity;
    }

    @SuppressWarnings("unchecked")
    private E reloadEntityFromRepository() {
        try {
            final Class<E> clazz = (Class<E>) ClassUtils.getClass(entityClass);
            return EventEntityManagerHolder.getInstance().getEventEntityManager().findEntity(getTenant(), getEntityId(),
                    clazz);
        } catch (final ClassNotFoundException e) {
            LOG.error("Cannot reload entity because class is not found", e);
        }
        return null;
    }

}
