Default Graph package
**********************

This subpackage contains the default graph related interfaces and implementations used in this tool. Using it, in addition of all the graph generic features, it supports:           

* Define nodes with or without graphical representations (such as nodes for shapes or resulting fact nodes when using cross-domain graph interactions)
      * DefaultNode -> Nodes with non graphical representation. Can be nodes that emerge from cross-domain interactions (fact nodes).
      * ViewNode -> Nodes with a graphical representation (Definition).                
* Define two type of relationships: connection edges (with a view representation) AND any other kind of relationships without view representations.      
      * DefaultEdge -> Edges with non graphical representation. The semantics for the relationship are given by the `relation name` attribute.         
      * ViewEdge ->  Edges with a graphical representation (Definition).The semantics for the edge relationship are given by the wirez Definition.            
* Ad-hoc support for parent-child entity relationships.           
      * ChildRelationEdge -> It's a DefaultEdge implementation for parent-child relationships.             
