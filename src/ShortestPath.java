import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestPath {
    //Hypergraph er det samme som hyperpath
    //Class attributes
    private PriorityQueue<Vertex> pq = new PriorityQueue<>();
    private ArrayList<Edge> path = new ArrayList<>();

    private ArrayList<Edge> SBT(Hypergraph hypergraph, Vertex source, Vertex target){
        for (Vertex vertex : hypergraph.getVertices()) {
            vertex.setCost(Integer.MAX_VALUE);
        }
        for (Edge edge : hypergraph.getEdges()) {
            edge.setKj(0);
        }
        source.setCost(0);
        pq.add(source);
        while (pq.size() > 0) {
            Vertex u = pq.poll(); //Retrieves and removes first element
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) må være u's outgoing edges
                edge.setKj(edge.getKj()+1); //legal?
                if (edge.getKj() == edge.getTail().size()) {
                    if (u == target) {
                        getPath(source, u); // Array of a path of edges, perhaps return it?
                        return path;
                    }
                    int f = costFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    if (!pq.contains(y)){ // if pq doesn't contain head of current edge
                        pq.add(y);
                    }
                    y.setCost(f);
                    y.setPredecessor(edge);
                }
            }
        }
        throw new IllegalArgumentException("Couldn't find a path from source to target");
    }

    private int costFunction(Edge edge){
        int edgeCost = edge.getCost();
        int edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            edgeTailCost =+ v.getCost();
        }
        return edgeCost + edgeTailCost;
    }

    private void getPath(Vertex source, Vertex target){
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
}
