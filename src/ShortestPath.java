import java.lang.reflect.Array;
import java.util.*;

public class ShortestPath {
    //Hypergraph er det samme som hyperpath
    //Class attributes
    private PriorityQueue<Vertex> pq = new PriorityQueue<>((o1, o2) -> o1.getCost() - o2.getCost());
    private ArrayList<Edge> path = new ArrayList<>();

    public void printPath(){
        path.sort((o1, o2) -> o1.getId() - o2.getId());

        for (Edge edge :path) {
            System.out.println(edge.getId()+1);
            System.out.println("Cost: " + edge.getCost());
        }
    }

    public ShortestPath(Hypergraph hypergraph, Vertex source, Vertex target){
        SBT(hypergraph,source,target);
    }

    private ArrayList<Edge> SBT(Hypergraph hypergraph, Vertex source, Vertex target){
        for (Vertex vertex : hypergraph.getVertices()) {
            vertex.setCost(Integer.MAX_VALUE);
        }
        source.setCost(0);
        pq.add(source);
        while (pq.size() > 0) {
            Vertex u = pq.poll(); //Retrieves and removes first element
            if (u.getId() == target.getId()) {
                getPath(source, u); // Array of a path of edges, perhaps return it?
                return path;
            }
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) må være u's outgoing edges
                edge.setKj(edge.getKj()+1);
                if (edge.getKj() == edge.getTail().size()) {
                    int f = costFunction(edge); // Some cost function
                    Vertex y = edge.getHead();
                    if(y.getCost() > f){
                        if (!pq.contains(y)){ // if pq doesn't contain head of current edge
                            pq.add(y);
                        }
                        y.setCost(f);
                        y.setPredecessor(edge);
                    }
                }
            }
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

    // not really good at finding a path maybe find a new method
    private void getPath(Vertex source, Vertex target){
        if(!path.contains(target.getPredecessor())){
            path.add(target.getPredecessor());
        }
        if (target == source) {
            return;
        }
        for (Vertex vertex : target.getPredecessor().getTail()) {
            if (vertex.getPredecessor() != null) { // If the vertex doesn't have a predecessor it hasn't been visited.
                getPath(source, vertex);
            }
        }
    }

    public ArrayList<Edge> getShortestPath(){
        path.sort(((o1, o2) -> o2.getId()-o1.getId()));
        return path;
    }

}
