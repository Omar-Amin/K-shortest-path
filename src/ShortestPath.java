import java.util.*;

public class ShortestPath {

    WeightingFunctions functions;

    public ShortestPath(function toUse){
        this.functions = new WeightingFunctions(toUse);
    }

    /**
     * SBT is an algorithm from "Directed hypergraphs and applications", which acts like Dijkstra.
     *
     * @return Pair(Path,Cost), where path is a list of edges and cost is an integer (cost of the path)
     * */
    public Hyperpath SBT(Hypergraph hypergraph, Vertex source, Vertex target, HashMap<Integer,Integer> deletedEdges,boolean runUntilEmpty){
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
            if(target == u && functions.getFunctionType() != function.min && !runUntilEmpty){
                return getPath(source, target, deletedEdges);
            }
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) is u's outgoing edges
                if(deletedEdges.get(edge.getId()) != null){
                    continue;
                }
                edge.setKj(edge.getKj()+1);
                if (edge.getKj() == edge.getTail().size()) {
                    double f = functions.costFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    if(y.getCost() > f){
                        // if pq doesn't contain head of current edge
                        if(!pq.contains(y)){
                            // this line can be removed if we return immediately
                            if(y.getCost() < Double.MAX_VALUE){
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
     * This method gets path by topological sorting.
     *
     * @return Pair object which consist of the path and a cost
     * NOTE: The path is an array of edges */
    private Hyperpath getPath(Vertex source, Vertex target, HashMap<Integer,Integer> deletedEdges){
        // a map for checking if the edge is visited
        // counter for ingoing for each vertex
        ArrayList<Edge> path = new ArrayList<>();
        HashMap<Vertex,Integer> in = edgesInPath(target);
        // queue which sorts by ID in order to get the same topological order for the same path
        PriorityQueue<Vertex> zeroIn = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.getId(), o2.getId()));
        zeroIn.add(target);
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
    private HashMap<Vertex, Integer> edgesInPath(Vertex target){
        HashMap<Vertex, Integer> in = new HashMap<>();
        HashMap<Edge, Integer> edges = new HashMap<>();

        ArrayDeque<Vertex> stack = new ArrayDeque<>();
        stack.push(target);
        while (!stack.isEmpty()){
            target = stack.pop();
            Edge predecessor = target.getPredecessor();
            if(edges.get(predecessor) != null){
                continue;
            }
            edges.put(predecessor,0);
            for (Vertex vertex : predecessor.getTail()) {
                // counts how many outgoing edges each vertex in the shortest path
                in.putIfAbsent(vertex, 0);
                in.put(vertex,in.get(vertex)+1);
                if (vertex.getPredecessor() != null) { // If the vertex doesn't have a predecessor it hasn't been visited.
                    stack.push(vertex);
                }
            }
        }

        return in;
    }

}
