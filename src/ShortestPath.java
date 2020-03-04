import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class ShortestPath {
    //Hypergraph er det samme som hyperpath
    //Class attributes
    private PriorityQueue<Vertex> pq = new PriorityQueue<>();

    private Hypergraph SBT(Hypergraph hypergraph, Vertex source, Vertex target){
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
                        // Vi har fundet vores target, returner path
                    }
                    int f = costFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    if (!pq.contains(y)){ // if pq doesn't contain head of current edge
                        pq.add(y);
                    }
                    y.setCost(edge.getCost() + f);
                    y.setPredecessor(edge);
                }
            }
        }
        return hypergraph;
    }

    private int costFunction(Edge edge){
        int edgeCost = edge.getCost();
        int edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            edgeTailCost =+ v.getCost();
        }
        return edgeCost + edgeTailCost;
    }

    private ArrayList<Edge> getPath(Vertex source, Vertex target){
        ArrayList<Edge> path = new ArrayList<>();
        Vertex tempTarget = target;
        while (true) {
            for (Edge edge : source.getOutgoing_edges()) {
                if (target.getPredecessor() == edge) {
                    return path;
                }
            }
            path.add(target.getPredecessor());
        }
    }

    private Vertex getCheapestVertex(Edge edge){
        Vertex cheapestVert = new Vertex(Integer.MAX_VALUE); // Skal give den et ID...
        cheapestVert.setCost(Integer.MAX_VALUE);
        for (Vertex vertex : edge.getTail()){
            if (vertex.getCost() < cheapestVert.getCost()) {
                cheapestVert = vertex;
            }
        }
        return cheapestVert;
    }

}
