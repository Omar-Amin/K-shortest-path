import com.sun.javafx.image.IntPixelGetter;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.lang.reflect.Parameter;
import java.util.*;

public class ShortestPath {
    //Hypergraph er det samme som hyperpath
    //Class attributes
    private PriorityQueue<Vertex> pq = new PriorityQueue<>(new Comparator<Vertex>() {
        @Override
        public int compare(Vertex o1, Vertex o2) {
            return Integer.compare(o1.getCost(), o2.getCost());
        }
    });
    //private minPQ pq = new minPQ();
    private ArrayList<Edge> path = new ArrayList<>();
    private int cost = 0;

    public void printPath(){
        path.sort((o1, o2) -> o1.getId() - o2.getId());
        System.out.println("Graph printed:");
        for (Edge edge :path) {
            System.out.println("Edge id: " + (edge.getId()+1));
            System.out.println("Cost of edge: " + edge.getCost());
        }
    }

    public ShortestPath(Hypergraph hypergraph, Vertex source, Vertex target, ArrayList<Edge> deletedEdges){
        SBT(hypergraph,source,target,deletedEdges);
    }

    private ArrayList<Edge> SBT(Hypergraph hypergraph, Vertex source, Vertex target,ArrayList<Edge> deletedEdges){
        for (Vertex vertex : hypergraph.getVertices()) {
            vertex.setCost(Integer.MAX_VALUE);
        }
        for (Edge edge : hypergraph.getEdges()) {
            edge.setKj(0);
        }
        source.setCost(0);
        pq.add(source);
        while (pq.size() > 0) {
            //debugPrintQueue();
            Vertex u = pq.poll(); //Retrieves and removes first element
            //System.out.println("Popped: " + (u.getId()+1));
            //System.out.println("pq size: " + pq.size());
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) må være u's outgoing edges
                if(deletedEdges.contains(edge)){
                    continue;
                }
                edge.setKj(edge.getKj()+1);
                if (edge.getKj() >= edge.getTail().size()) {
                    int f = minCostFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    //System.out.println("Head: " + (y.getId()+1));
                    //System.out.println("Head cost: " + y.getCost());
                    //System.out.println("Costfunction: " + f);
                    if(y.getCost() > f){
                        // if pq doesn't contain head of current edge
                        pq.remove(y);
                        y.setCost(f);
                        y.setPredecessor(edge);
                        pq.add(y);
                    }
                }
            }
            //System.out.println("________");
        }

        if(target.getCost() != Integer.MAX_VALUE){
            cost = target.getCost();
            getPath(source, target); // Array of a path of edges, perhaps return it?
            return path;
        }

        throw new IllegalArgumentException("Couldn't find a path from source to target");
    }

    /**
     * Function that takes in an edge and find the cost value for
     * the given edge, by getting the cost of all of its tail vertices
     * and adding its own cost.
     *
     * @param edge: The edge to find the cost
     * */
    private int costFunction(Edge edge){
        int edgeCost = edge.getCost();
        int edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            edgeTailCost += v.getCost();
        }
        return edgeCost + edgeTailCost;
    }

    /**
     * Minimum cost function.
     * */
    private int minCostFunction(Edge edge){
        int edgeCost = edge.getCost();
        int edgeTailCost = Integer.MAX_VALUE;
        for (Vertex v : edge.getTail()) {
            if(v.getCost() < edgeTailCost){
                edgeTailCost = v.getCost();
            }
        }
        return edgeCost + edgeTailCost;
    }

    // not really good at finding a path maybe find a new method
    private void getPath(Vertex source, Vertex target){
        if(path.contains(target.getPredecessor())){
            return;
        }
        path.add(target.getPredecessor());
        if (target == source) {
            return;
        }
        for (Vertex vertex : target.getPredecessor().getTail()) {
            if (vertex.getPredecessor() != null) { // If the vertex doesn't have a predecessor it hasn't been visited.
                getPath(source, vertex);
            }
        }
    }

    public Pair<ArrayList<Edge>, Integer> getShortestPath(){
        path.sort((o1, o2) -> o1.getId() - o2.getId());
        return new Pair<>(path,cost);
    }

    public int getCost(){
        return cost;
    }

/*    private void debugPrintQueue(){
        ArrayList<Vertex> tmp = new ArrayList<>();
        while (pq.size() > 0){
            Vertex v = pq.poll();
            //System.out.println("Vertex id: " + (v.getId()+1));
            //System.out.println("Cost: " + v.getCost());
            tmp.add(v);
        }
        pq.addAll(tmp);
    }*/

}
