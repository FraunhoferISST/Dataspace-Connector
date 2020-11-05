package de.fraunhofer.isst.dataspaceconnector.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

/**
 * This class provides a custom data resource with an id, data and metadata to be saved in a h2 database.
 *
 * @author Julia Pampus
 * @version $Id: $Id
 */
@Data
@Entity
@Table
public class OfferedResource implements ConnectorResource{
    @Id
    @JsonProperty("uuid")
    private UUID uuid;

    @JsonProperty("created")
    private Date created;

    @JsonProperty("modified")
    private Date modified;

    @NotNull
    @Column(columnDefinition = "BYTEA")
    @JsonProperty("metadata")
    private ResourceMetadata resourceMetadata;

    @Column(columnDefinition = "TEXT")
    @JsonProperty("data")
    private String data;

    /**
     * <p>Constructor for OfferedResource.</p>
     */
    public OfferedResource() {

    }

    /**
     * <p>Constructor for OfferedResource.</p>
     *
     * @param uuid a {@link java.util.Date} object.
     * @param created a {@link java.util.Date} object.
     * @param modified a {@link java.util.Date} object.
     * @param resourceMetadata a {@link de.fraunhofer.isst.dataspaceconnector.model.ResourceMetadata} object.
     * @param data a {@link java.lang.String} object.
     */
    public OfferedResource(UUID uuid, Date created, Date modified, ResourceMetadata resourceMetadata, String data) {
        this.uuid = uuid;
        this.created = created;
        this.modified = modified;
        this.resourceMetadata = resourceMetadata;
        this.data = data;
    }

    /** {@inheritDoc} */
    @Override
    public UUID getUuid() {
        return uuid;
    }

    /** {@inheritDoc} */
    @Override
    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /** {@inheritDoc} */
    @Override
    public Date getCreated() {
        return created;
    }

    /** {@inheritDoc} */
    @Override
    public void setCreated(Date created) {
        this.created = created;
    }

    /** {@inheritDoc} */
    @Override
    public Date getModified() {
        return modified;
    }

    /** {@inheritDoc} */
    @Override
    public void setModified(Date modified) {
        this.modified = modified;
    }

    /** {@inheritDoc} */
    @Override
    public ResourceMetadata getResourceMetadata() {
        return resourceMetadata;
    }

    /** {@inheritDoc} */
    @Override
    public void setResourceMetadata(ResourceMetadata resourceMetadata) {
        this.resourceMetadata = resourceMetadata;
    }

    /** {@inheritDoc} */
    @Override
    public String getData() {
        return data;
    }

    /** {@inheritDoc} */
    @Override
    public void setData(String data) {
        this.data = data;
    }
}
