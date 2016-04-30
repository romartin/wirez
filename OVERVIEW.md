Key points
===========

Domain model
------------

The domain model objects, such as view definitions, properties or rules are intended to be flexible and most error-free as possible.                          

You can provide either domain models based on Java POJO classes, which provide high type-safe programming models so any error is found earlier at compile time, or you can just provide domain models that come from JSON or XML files.                           

The Adapters API provides this flexibility to introspect any java beans to obtain our domain models:                              
* [Adapters API](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/definition/adapter)                             
* [Definition Manager](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/DefinitionManager.java)                              
* [Definition adapter](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/definition/adapter/DefinitionAdapter.java)                         

**Annotated POJO domain models**                  

The domain model can be provided by annotating any external object models:              
* [Current BPMN model classes](https://github.com/romartin/wirez/tree/master/wirez-sets/wirez-bpmn/wirez-bpmn-api/src/main/java/org/wirez/bpmn/api)                         
* [A BPMN Task](https://github.com/romartin/wirez/blob/master/wirez-sets/wirez-bpmn/wirez-bpmn-api/src/main/java/org/wirez/bpmn/api/Task.java)                           
* [The height property](https://github.com/romartin/wirez/blob/master/wirez-sets/wirez-bpmn/wirez-bpmn-api/src/main/java/org/wirez/bpmn/api/property/Height.java), used for example by a Task.                          

Dynamic rule constraints
-------------------------

The way you give semantics to the diagrams is using rules. Rules are dynamically evaluated at runtime on each operation that is potentially done on the diagram and they define the constraints, the semantics, the flow....                

Rules can be given in two ways as well: in your domain model classes or provided by definitions in JSON, XML files, etc                      

Adding rule constraints into a given domain model it's easy as adding those kind of annotations to you domain objects:                      
* [Containment rules](https://github.com/romartin/wirez/blob/master/wirez-sets/wirez-bpmn/wirez-bpmn-api/src/main/java/org/wirez/bpmn/api/Lane.java#L38)                                   
* [Connection rules](https://github.com/romartin/wirez/blob/master/wirez-sets/wirez-bpmn/wirez-bpmn-api/src/main/java/org/wirez/bpmn/api/SequenceFlow.java#L38)                            
* [Cardinality rules](https://github.com/romartin/wirez/blob/master/wirez-sets/wirez-bpmn/wirez-bpmn-api/src/main/java/org/wirez/bpmn/api/SequenceFlow.java#L44)                                 

All rules are evaluated using an API that supports both evaluations for domain objects and graph elements:                       
* [Rules API](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/rule)                                      
* [Model Rule Management](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/rule/model)                               
* [Graph Rule Management](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/rule/graph)                                

Graph support
-------------

The Graph API is based on the labeled property graph model, which provides a flexible model that can includes quite most of the typical diagrams a user should need, such as E-R diagrans, trees, UML, hierarchy diagrams, etc                        

* [Graph API](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/graph)                                
* [A Node](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/graph/Node.java)                                
* [An Edge](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/graph/Edge.java)                                
* [Available content for nodes and edges](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/graph/content)                                

**Mutations and Commands**                            

The construction of the graph has to be consistent with the semantics and rules given for its kind/type, so all the updates and changes that can be done in the graph, such as adding nodes, 
changing connections, changing properties of the domain objects, etc are done through commands - commands ensure successful updates to the graph, provides the graph incremental 
operations between user sessions and graph snapshots, provides the undo and redo features and much more.                        

* [The Command API](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/command)                                      
* [The Command Manager](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/command/CommandManager.java)                             
* [The Batch Command Manager](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/command/batch/BatchCommandManager.java)                      
* [The Canvas Command Manager](https://github.com/romartin/wirez/blob/master/wirez-core/wirez-core-client/src/main/java/org/wirez/core/client/canvas/command/CanvasCommandManager.java)                      
* [Available commands for a graph](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-client/src/main/java/org/wirez/core/client/canvas/command/impl)                           
* [Available commands for a canvas](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-client/src/main/java/org/wirez/core/client/canvas/command/impl)                        

Lookup processors
-----------------

There exist a look-up API available for querying the domain model and rules dynamically:                     
 
 * [Lookup API](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/lookup)                           
 * [Rule Lookup Manager](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/lookup/rule)                                  
 * [Definition Lookup Manager](https://github.com/romartin/wirez/tree/master/wirez-core/wirez-core-api/src/main/java/org/wirez/core/api/lookup/definition)                              
 
Backend
-------

The backend is prepared to support multi-tenant architectures so provide different implementations for managing the sessions, diagrams, etc.                   


Client
------

View isolation
--------------


Client sessions and controls
-----------------------------

External Sets
--------------

