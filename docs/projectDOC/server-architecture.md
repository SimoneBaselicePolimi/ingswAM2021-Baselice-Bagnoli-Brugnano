#Server architecture: a quick overview
The goal of this document is to give an overview of the main components in our design, how they work and how they are 
connected together. We also wanted to give some insights into the reasons for the design choices we made.   

##The main components
When a client sends a message to the server, the message gets parsed and a new ClientRequest will be created. 
The Controller is the component that, after having received a client message, will be responsible for creating the 
appropriate ClientRequest, calling the GameManager to do the actual handling of the request and eventually answering the 
client(s) with the message returned by the GameManager.

###Controller: ClientRequest and ServerMessage 
Every different type of message that a client can send to the server is modelled using a different concrete 
implementation of ClientRequest. 
<< TODO >>

###GameManager and GameStates
<< TODO >>

###GameContext
<< TODO >>

###Notifier
<< TODO >>


##Design choices and Design Patterns used 
strategy, visitor, state, factory method, builder ...
<< TODO >>

