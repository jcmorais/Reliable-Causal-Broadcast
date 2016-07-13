# Reliable-Causal-Broadcast
@Universidade do Minho, Reliable Distributed Systems

## Synopsis
A common implementation strategy for a reliable causal broadcast service is to assign a vector clock to each message broadcast and use the causality information in the vector clock to decide at each destination when a message can be delivered. If a message arrives at a given destination before causally preceding messages have been delivered, the service delays delivery of that message until those messages arrive and are delivered. Unlike totally ordered broadcast,
which requires a global consensus on the delivery order, causal broadcast can progress with local decisions. For general datatypes, causal consistency is likely the strongest consistency criteria compatible with an always-available system
that eventually converges.

## Implementation
This code is an example of its implementation in Java using the ZeroMQ library to send messages between nodes.

## References
Carlos Baquero, Paulo Sérgio Almeida, and Ali Shoker: Making Operation-based CRDTs Operation-based
