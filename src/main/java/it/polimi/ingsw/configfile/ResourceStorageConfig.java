package it.polimi.ingsw.configfile;

import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.server.model.storage.MaxResourceNumberRule;
import it.polimi.ingsw.server.model.storage.ResourceStorageRule;
import it.polimi.ingsw.server.model.storage.SameResourceTypeRule;
import it.polimi.ingsw.server.model.storage.SpecificResourceTypeRule;

import java.util.List;

public class ResourceStorageConfig {

    public StorageConfig storage;

    public static class StorageConfig {
        public final List<StorageConfig.RuleConfig> rules;

        public StorageConfig(List<StorageConfig.RuleConfig> rules) {
            this.rules = rules;
        }

        public abstract static class RuleConfig {
            public abstract ResourceStorageRule createRule();
        }

        public static class MaxResourceNumberRuleConfig extends RuleConfig {
            public final int maxNumber;

            public MaxResourceNumberRuleConfig(int maxNumber) {
                this.maxNumber = maxNumber;
            }

            public ResourceStorageRule createRule() {
                return new MaxResourceNumberRule(maxNumber);
            }

        }

        public static class SpecificResourceTypeRuleConfig extends RuleConfig {
            public final ResourceType resourceType;

            public SpecificResourceTypeRuleConfig(ResourceType resourceType) {
                this.resourceType = resourceType;
            }

            public ResourceStorageRule createRule() {
                return new SpecificResourceTypeRule(resourceType);
            }
        }

        public static class SameResourceTypeRuleConfig extends RuleConfig {
            public ResourceStorageRule createRule() {
                return new SameResourceTypeRule();
            }
        }
    }
}
