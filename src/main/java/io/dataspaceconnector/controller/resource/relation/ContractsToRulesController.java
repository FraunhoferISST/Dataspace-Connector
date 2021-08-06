package io.dataspaceconnector.controller.resource.relation;

import io.dataspaceconnector.config.BasePath;
import io.dataspaceconnector.config.BaseType;
import io.dataspaceconnector.controller.resource.base.BaseResourceChildController;
import io.dataspaceconnector.controller.resource.base.tag.ResourceDescription;
import io.dataspaceconnector.controller.resource.base.tag.ResourceName;
import io.dataspaceconnector.controller.resource.view.rule.ContractRuleView;
import io.dataspaceconnector.model.rule.ContractRule;
import io.dataspaceconnector.service.resource.relation.ContractRuleLinker;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Offers the endpoints for managing the relations between contracts and rules.
 */
@RestController
@RequestMapping(BasePath.CONTRACTS + "/{id}/" + BaseType.RULES)
@Tag(name = ResourceName.CONTRACTS, description = ResourceDescription.CONTRACTS)
public class ContractsToRulesController extends BaseResourceChildController<
        ContractRuleLinker, ContractRule, ContractRuleView> {
}
