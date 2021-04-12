
| Property Name                                         | Standard value | Description                                                                                            |
|-------------------------------------------------------|--------------- |--------------------------------------------------------------------------------------------------------|
| maxNumberOfPlayers                                    | 4              | Maximum number of Players allowed in a Game                                                            |
| singlePlayerEnabled                                   | true           | Enables the Single Player mode                                                                         |
| gameSetup.numberOfLeadersCardsGivenToThePlayer        | 4              | Number of Leader Cards each Player randomly receives at the start of the Game                          |
| gameSetup.numberOfLeadersCardsThePlayerKeeps          | 2              | Number of Leader Cards the Player must choose from the ones given at the start of the Game             |
| initialPlayerResourcesBasedOnPlayOrder.starResource   | [0,2]          | Initial Star Resources (the Player has to choose which type of Resource he wants) based on Play Order  |
| initialPlayerResourcesBasedOnPlayOrder.faithPoints    | [0,1]          | Number of initial Faith Points based on Play Order                                                     |
| numberOfPlayerOwnedDevelopmentCardDecks               | 3              | Number of Slots for Development Cards assigned to each Player's Personal Board                         |
| basicProductionPower.costs.resources                  | {}             | Cost made of specific type and number of Resources                                                     |
| basicProductionPower.costs.starResources              | 2              | Cost made of a generic type of Resource (Player can choose), in a fixed quantity                       |
| basicProductionPower.rewards.resources                | {}             | Reward made of specific type and number of Resources                                                   |
| basicProductionPower.rewards.starResources            | 1              | Reward made of a generic type of Resource (Player can choose), in a fixed quantity                     |
| basicProductionPower.rewards.faithPoints              | 0              | Reward made of a fixed number of Faith Points                                                          |
| resourceShelves.storage.rules.ruleType                |                | Type of Rule associated to the Shelves used as Resources Storages by the Player                        |
| resourceShelves.differentResourcesInDifferentStorages | true           | Enables the Rule which impose that different Resources must be located in different Storages.          |
| numberOfResourcesRewardingOneVictoryPoint             | 5              | Number of Resources that at the end of the Game rewards the Player with one Victory Point              |
