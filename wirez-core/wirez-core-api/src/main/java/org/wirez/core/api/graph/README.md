Graph package
*************

This package provides the graph implementation in which is based the Wirez modelling tool.                   

The graph implementation is based on **The Labeled Property Graph Model**:              

* A labeled property graph is made up of nodes, relationships (edges), properties, and labels.                    
* Nodes contain properties. Think of nodes as documents that store properties in
the form of arbitrary key-value pairs.                    
* Nodes can be tagged with one or more labels. Labels group nodes together, and
indicate the roles they play within the dataset.                 
* Relationships/edges connect nodes and structure the graph. A relationship always has a
direction, a single name, and a start node and an end node—there are no dangling
relationships. Together, a relationship’s direction and name add semantic clarity
to the structuring of nodes.                  
* Like nodes, relationships/edges can also have properties. The ability to add properties
to relationships is particularly useful for providing additional metadata for graph
algorithms, adding additional semantics to relationships (including quality and
weight), and for constraining queries at runtime, if needed.                   
* You can create more domain specific diagrams (such as E-R, hierarchical, etc) by providing rule constraints in the Definition Set.            
* NOTE about E-R diagrams: Despite being graphs, E-R diagrams allow only single, undirected, named relationships between entities.            

The subpackage `impl` contains the default graph interfaces implentation used in this tool. Using it, in addition of all the graph generic features, it supports:           
* Define nodes with or without graphical representations (such as nodes for shapes or resulting fact nodes when using cross-domain graph interactions)      
* Define two type of relationships: connection edges AND containment relationships      
