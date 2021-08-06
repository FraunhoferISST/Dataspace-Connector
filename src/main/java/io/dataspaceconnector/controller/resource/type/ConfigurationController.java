package io.dataspaceconnector.controller.resource.type;

import de.fraunhofer.ids.messaging.core.config.ConfigContainer;
import de.fraunhofer.ids.messaging.core.config.ConfigUpdateException;
import io.dataspaceconnector.config.BasePath;
import io.dataspaceconnector.controller.resource.base.BaseResourceController;
import io.dataspaceconnector.controller.resource.base.tag.ResourceDescription;
import io.dataspaceconnector.controller.resource.base.tag.ResourceName;
import io.dataspaceconnector.controller.resource.view.configuration.ConfigurationView;
import io.dataspaceconnector.controller.util.ResponseCode;
import io.dataspaceconnector.controller.util.ResponseDescription;
import io.dataspaceconnector.controller.util.ResponseUtils;
import io.dataspaceconnector.model.configuration.Configuration;
import io.dataspaceconnector.model.configuration.ConfigurationDesc;
import io.dataspaceconnector.service.resource.type.ConfigurationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

/**
 * Offers the endpoints for managing configurations.
 */
@RestController
@RequestMapping(BasePath.CONFIGURATIONS)
@RequiredArgsConstructor
@Tag(name = ResourceName.CONFIGURATIONS, description = ResourceDescription.CONFIGURATIONS)
public class ConfigurationController extends BaseResourceController<Configuration,
        ConfigurationDesc, ConfigurationView, ConfigurationService> {

    /**
     * The current connector configuration.
     */
    private final @NonNull ConfigContainer configContainer;

    /**
     * Configuration Service, to read and set current config in DB.
     */
    private final @NonNull ConfigurationService configurationSvc;

    /**
     * Update the connector's current configuration.
     *
     * @param toSelect The new configuration.
     * @return Ok or error response.
     */
    @PutMapping(value = "/{id}/active", consumes = {"*/*"})
    @Operation(summary = "Update current configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK),
            @ApiResponse(responseCode = ResponseCode.BAD_REQUEST,
                    description = ResponseDescription.BAD_REQUEST),
            @ApiResponse(responseCode = ResponseCode.UNAUTHORIZED,
                    description = ResponseDescription.UNAUTHORIZED),
            @ApiResponse(responseCode = ResponseCode.UNSUPPORTED_MEDIA_TYPE,
                    description = ResponseDescription.UNSUPPORTED_MEDIA_TYPE),
            @ApiResponse(responseCode = ResponseCode.INTERNAL_SERVER_ERROR,
                    description = ResponseDescription.INTERNAL_SERVER_ERROR)})
    @ResponseBody
    public ResponseEntity<Object> setConfiguration(
            @Valid @PathVariable(name = "id") final UUID toSelect) {
        try {
            configurationSvc.swapActiveConfig(toSelect);
        } catch (ConfigUpdateException exception) {
            return ResponseUtils.respondConfigurationUpdateError(exception);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Return the connector's current configuration.
     *
     * @return The configuration object or an error.
     */
    @GetMapping(value = "/active", produces = "application/hal+json")
    @Operation(summary = "Get current configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK),
            @ApiResponse(responseCode = ResponseCode.UNAUTHORIZED,
                    description = ResponseDescription.UNAUTHORIZED)})
    @ResponseBody
    public ConfigurationView getConfiguration() {
        return get(configurationSvc.getActiveConfig().getId());
    }

    /**
     * Return the connector's current configuration.
     *
     * @return The configuration object or an error.
     */
    @GetMapping(value = "/active", produces = "application/ld+json")
    @Operation(summary = "Get current configuration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = ResponseCode.OK, description = ResponseDescription.OK),
            @ApiResponse(responseCode = ResponseCode.UNAUTHORIZED,
                    description = ResponseCode.UNAUTHORIZED)})
    @ResponseBody
    public ResponseEntity<Object> getIdsConfiguration() {
        return ResponseEntity.ok(configContainer.getConfigurationModel().toRdf());
    }
}
