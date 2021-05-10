package io.dataspaceconnector.utils;

import de.fraunhofer.iais.eis.Contract;
import de.fraunhofer.iais.eis.ContractAgreement;
import de.fraunhofer.iais.eis.Rule;
import io.dataspaceconnector.exceptions.ContractException;
import io.dataspaceconnector.exceptions.ResourceNotFoundException;
import io.dataspaceconnector.model.Artifact;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.dataspaceconnector.utils.RuleUtils.compareObligations;
import static io.dataspaceconnector.utils.RuleUtils.comparePermissions;
import static io.dataspaceconnector.utils.RuleUtils.compareProhibitions;

public final class ContractUtils {

    /**
     * Constructor without params.
     */
    private ContractUtils() {
        // not used
    }

    /**
     * Return all contract rules as one list.
     *
     * @param contract The ids contract.
     * @return A list of ids rules.
     * @throws IllegalArgumentException If the message is null.
     */
    public static List<Rule> extractRulesFromContract(final Contract contract) {
        Utils.requireNonNull(contract, ErrorMessages.CONTRACT_NULL);
        final var permissionList = contract.getPermission();
        final var ruleList = permissionList == null ? new ArrayList<Rule>()
                : new ArrayList<Rule>(permissionList);

        final var prohibitionList = contract.getProhibition();
        if (prohibitionList != null) {
            ruleList.addAll(prohibitionList);
        }

        final var obligationList = contract.getObligation();
        if (obligationList != null) {
            ruleList.addAll(obligationList);
        }

        return ruleList;
    }

    /**
     * Iterate over all rules of a contract and add the ones with the element as their target to a
     * rule list.
     *
     * @param contract The contract.
     * @param element  The requested element.
     * @return List of ids rules.
     * @throws IllegalArgumentException If the message is null.
     */
    public static List<? extends Rule> getRulesForTargetId(final Contract contract,
                                                           final URI element) {
        Utils.requireNonNull(contract, ErrorMessages.CONTRACT_NULL);
        final var rules = new ArrayList<Rule>();

        for (final var permission : contract.getPermission()) {
            final var target = permission.getTarget();
            if (target != null && target.equals(element)) {
                rules.add(permission);
            }
        }

        for (final var prohibition : contract.getProhibition()) {
            final var target = prohibition.getTarget();
            if (target != null && target.equals(element)) {
                rules.add(prohibition);
            }
        }

        for (final var obligation : contract.getObligation()) {
            final var target = obligation.getTarget();
            if (target != null && target.equals(element)) {
                rules.add(obligation);
            }
        }

        return rules;
    }

    /**
     * Extract targets and save them together with the respective rules to a map.
     *
     * @param rules List of ids rules.
     * @return Map with targets and matching rules.
     */
    public static Map<URI, List<Rule>> getTargetRuleMap(final List<Rule> rules) {
        final var targetRuleMap = new HashMap<URI, List<Rule>>();

        // Iterate over all rules.
        for (final var rule : rules) {
            // Get target of rule.
            final var target = rule.getTarget();

            // If the target is already in the map, add the rule to the value list.
            if (targetRuleMap.containsKey(target)) {
                final var value = targetRuleMap.get(target);
                value.add(rule);
            } else {
                // If not, create a target-rule-entry to the map.
                final var value = new ArrayList<Rule>();
                value.add(rule);
                targetRuleMap.put(target, value);
            }
        }

        return targetRuleMap;
    }

    /**
     * Check if contract offer has a restricted consumer. If the value does not match the issuer
     * connector, remove the contract from the list.
     *
     * @param issuerConnector The requesting consumer.
     * @param contracts       List of contracts.
     * @return Cleaned list of contracts.
     * @throws IllegalArgumentException if any of the arguments is null.
     */
    public static List<io.dataspaceconnector.model.Contract> removeContractsWithInvalidConsumer(
            final List<io.dataspaceconnector.model.Contract> contracts,
            final URI issuerConnector) {
        Utils.requireNonNull(contracts, ErrorMessages.LIST_NULL);
        Utils.requireNonNull(issuerConnector, ErrorMessages.URI_NULL);

        return contracts.parallelStream()
                .filter(x -> x.getConsumer().equals(issuerConnector) || x.getConsumer()
                        .toString()
                        .isBlank())
                .collect(Collectors.toList());
    }

    /**
     * Check if the transfer contract's target matches the requested artifact.
     *
     * @param artifacts         List of artifacts.
     * @param requestedArtifact Id of the requested artifact.
     * @return True if the requested artifact matches the transfer contract's artifacts.
     * @throws ResourceNotFoundException If a resource could not be found.
     */
    public static boolean isMatchingTransferContract(final List<Artifact> artifacts,
                                                     final URI requestedArtifact)
            throws ResourceNotFoundException {
        for (final var artifact : artifacts) {
            final var endpoint = SelfLinkHelper.getSelfLink(artifact);
            if (endpoint.equals(requestedArtifact)) {
                return true;
            }
        }

        // If the requested artifact could not be found in the transfer contract (agreement).
        return false;
    }

    /**
     * Validate if the assigner is the expected one.
     *
     * @param agreement The contract agreement.
     * @throws ContractException If the assigner is not as expected.
     */
    public static void validateRuleAssigner(final ContractAgreement agreement)
            throws ContractException {
        // TODO implement later
        // NOTE: Recipient url might not be the connector id.
    }

    /**
     * Compare two contract agreements to each other.
     *
     * @param consumer The consumer agreement.
     * @param provider The provider agreement.
     * @return True if both agreements are equal.
     * @throws ContractException If both objects do not match.
     */
    public static boolean compareContractAgreements(final ContractAgreement consumer,
                                                    final ContractAgreement provider) {
        return consumer.getId().equals(provider.getId())
                && comparePermissions(consumer.getPermission(), provider.getPermission())
                && compareProhibitions(consumer.getProhibition(), provider.getProhibition())
                && compareObligations(consumer.getObligation(), provider.getObligation());
    }
}
