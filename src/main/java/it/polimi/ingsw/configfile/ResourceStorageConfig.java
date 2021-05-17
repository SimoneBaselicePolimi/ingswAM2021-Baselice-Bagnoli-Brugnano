package it.polimi.ingsw.configfile;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.storage.MaxResourceNumberRule;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;
import it.polimi.ingsw.server.model.storage.SameResourceTypeRule;
import it.polimi.ingsw.server.model.storage.SpecificResourceTypeRule;

import java.util.List;

/**
 * This class sets all the parameters regarding the Resource Storages used by each Player to keep Resources bought
 * at the Market.
 */

public class ResourceStorageConfig {

    /**
     * Type of Resource Storage
     */
    public StorageConfig storage;

    /**
     * This class contains the configuration schema of a Resource Storage.
     */
    public static class StorageConfig {

        /**
         * List of Rules implemented by the Resource Storage
         */
        public final List<StorageConfig.RuleConfig> rules;

        /**
         * StorageConfig constructor
         * @param rules list of Rules implemented by the Resource Storage
         */
        public StorageConfig(
            @JsonProperty("rules") List<StorageConfig.RuleConfig> rules
        ) {
            this.rules = rules;
        }

        /**
         * This class contains the configuration schema of a Rule implemented by a Storage.
         */
        @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "ruleType")
        @JsonSubTypes({
            @JsonSubTypes.Type(value = MaxResourceNumberRuleConfig.class, name = "MAX_RESOURCE_NUMBER"),
            @JsonSubTypes.Type(value = SpecificResourceTypeRuleConfig.class, name = "SPECIFIC_RESOURCE_TYPE"),
            @JsonSubTypes.Type(value = SameResourceTypeRuleConfig.class, name = "SAME_RESOURCE_TYPE")
        })
        public abstract static class RuleConfig {

            /**
             * Abstract method to create a specific Rule
             * @return a ResourceStorageRule
             */
            public abstract ResourceStorageRule createRule();
        }

        /**
         * If the storage implements this Rule, the Storage has a maximum number of Resources (it has a limited space).
         * If the storage is full, it is not possible to add resources until some are removed.
         */
        @JsonTypeName("MAX_RESOURCE_NUMBER")
        public static class MaxResourceNumberRuleConfig extends RuleConfig {

            /**
             * Max number of Resources that the Storage can contain
             */
            public final int maxNumber;

            /**
             * MaxResourceNumberRuleConfig constructor.
             * @param maxNumber max number of Resources that the Storage can contain
             */
            public MaxResourceNumberRuleConfig(
                @JsonProperty("maxNumber") int maxNumber
            ) {
                this.maxNumber = maxNumber;
            }

            /**
             * Specific implementation of the abstract method of RuleConfig class
             * @return a new MaxResourceNumberRule
             */
            public ResourceStorageRule createRule() {
                return new MaxResourceNumberRule(maxNumber);
            }
        }

        /**
         * If the Storage implements this Rule, only a certain type of Resources can be added to the Storage.
         */
        @JsonTypeName("SPECIFIC_RESOURCE_TYPE")
        public static class SpecificResourceTypeRuleConfig extends RuleConfig {

            /**
             * Specific type of Resource that the Storage can contain
             */
            public final ResourceType resourceType;

            /**
             * SpecificResourceTypeRuleConfig constructor.
             * @param resourceType specific type of Resource that the Storage can contain
             */
            public SpecificResourceTypeRuleConfig(
                @JsonProperty("resourceType") ResourceType resourceType
            ) {
                this.resourceType = resourceType;
            }

            /**
             * Specific implementation of the abstract method of RuleConfig class
             * @return a new SpecificResourceTypeRule
             */
            public ResourceStorageRule createRule() {
                return new SpecificResourceTypeRule(resourceType);
            }
        }

        /**
         * If the Storage implements this Rule, the Storage can only contain Resources that are equal to each other
         * (different types of Resources can not be present)
         */
        @JsonTypeName("SAME_RESOURCE_TYPE")
        public static class SameResourceTypeRuleConfig extends RuleConfig {

            /**
             * Specific implementation of the abstract method of RuleConfig class
             * @return a new SameResourceTypeRule
             */
            public ResourceStorageRule createRule() {
                return new SameResourceTypeRule();
            }
        }
    }
}
