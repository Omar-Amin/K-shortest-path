import javafx.util.Pair;

import java.util.*;

public class ShortestPath {

    public ShortestPath(){
    }

    /**
     * SBT is an algorithm from "Directed hypergraphs and applications", which acts like Dijkstra.
     *
     * @return Pair(Path,Cost), where path is a list of edges and cost is an integer (cost of the path)
     * */
    public Hyperpath SBT(Hypergraph hypergraph, Vertex source, Vertex target, ArrayList<Edge> deletedEdges){
        minPQ pq = new minPQ();
        for (Vertex vertex : hypergraph.getVertices()) {
            vertex.setCost(Double.MAX_VALUE);
            vertex.setPredecessor(null);
        }
        for (Edge edge : hypergraph.getEdges()) {
            edge.setKj(0);
        }
        source.setCost(0);
        pq.insert(source);
        while (pq.size() > 0) {
            //debugPrintQueue();
            Vertex u = pq.popMin(); //Retrieves and removes first element
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) is u's outgoing edges
                if(deletedEdges.contains(edge)){
                    continue;
                }
                edge.setKj(edge.getKj()+1);
                if (edge.getKj() == edge.getTail().size()) {
                    double f = costFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    if(y.getCost() > f){
                        // if pq doesn't contain head of current edge
                        if(!pq.contains(y)){
                            // this line can be removed if we return immediately
                            if(y.getCost() < Integer.MAX_VALUE){
                                for (Edge e :y.getOutgoing_edges()) {
                                    e.setKj(e.getKj()-1);
                                }
                            }
                            y.setCost(f);
                            pq.insert(y);
                        }else {
                            y.setCost(f);
                            pq.decreaseValue(y.getId(),y.getCost());
                        }
                        y.setPredecessor(edge);
                    }
                }
            }
        }

        if(target.getCost() != Double.MAX_VALUE){
            return getPath(source, target, deletedEdges);
        }

        return null;
    }

    /**
     * Function that takes in an edge and find the cost value for
     * the given edge, by getting the cost of all of its tail vertices
     * and adding its own cost.
     *
     * @param edge: The edge to find the cost
     * */
    private double costFunction(Edge edge){
        double edgeCost = edge.getCost();
        double edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            edgeTailCost += v.getCost();
        }
        return edgeCost + edgeTailCost;
    }

    /**
     * Minimum cost function.
     * */
    private double minCostFunction(Edge edge){
        double edgeCost = edge.getCost();
        double edgeTailCost = Integer.MAX_VALUE;
        for (Vertex v : edge.getTail()) {
            if(v.getCost() < edgeTailCost){
                edgeTailCost = v.getCost();
            }
        }
        return edgeCost + edgeTailCost;
    }

    /**
     * This method gets path by topological sorting.
     *
     * @return Pair object which consist of the path and a cost
     * NOTE: The path is an array of edges */
    private Hyperpath getPath(Vertex source, Vertex target, ArrayList<Edge> deletedEdges){
        // a map for checking if the edge is visited
        HashMap<Edge, Integer> edges = new HashMap<>();
        // counter for ingoing for each vertex
        HashMap<Vertex,Integer> in = new HashMap<>();
        edgesInPath(source,target,edges,in);
        // queue which sorts by ID in order to get the same topological order for the same path
        PriorityQueue<Vertex> zeroIn = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        zeroIn.add(target);
        ArrayList<Edge> path = new ArrayList<>();
        double cost = target.getCost();
        path.add(target.getPredecessor());
        while (!zeroIn.isEmpty()){
            Vertex vertex = zeroIn.poll();
            if (vertex == source){
                continue;
            }
            for (Vertex tail : vertex.getPredecessor().getTail()) {
                // decrease each value for the vertex if the edge polled out
                // is a part of its outgoing edge
                in.put(tail,in.get(tail) - 1);
                if(in.get(tail) <= 0 && tail != source){
                    zeroIn.add(tail);
                    path.add(tail.getPredecessor());
                }
            }
        }

        Collections.reverse(path);

        return new Hyperpath(path,cost,deletedEdges);
    }

    /**
     * Helper function that updates the "in" hashmap in order to
     * perform topological sorting.
     * */
    private void edgesInPath(Vertex source, Vertex target,HashMap<Edge, Integer> edges,HashMap<Vertex, Integer> in){
        if(edges.get(target.getPredecessor()) != null){
            return;
        }
        edges.put(target.getPredecessor(),0);
        if (target == source) {
            return;
        }
        for (Vertex vertex : target.getPredecessor().getTail()) {
            // counts how many outgoing edges each vertex in the shortest path
            in.putIfAbsent(vertex, 0);
            in.put(vertex,in.get(vertex)+1);
            if (vertex.getPredecessor() != null) { // If the vertex doesn't have a predecessor it hasn't been visited.
                edgesInPath(source, vertex,edges, in);
            }
        }
    }

}
