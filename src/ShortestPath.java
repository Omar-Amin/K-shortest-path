import java.util.PriorityQueue;

public class ShortestPath {
    public ShortestPath(Hypergraph h, Vertex source){
        SBT(h);
    }
    //Hypergraph er det samme som hyperpath
    //Class attributes
    private PriorityQueue<Vertex> queue;

    private Hypergraph SBT(Hypergraph hypergraph, Vertex source, Vertex target){
        for (Vertex vertex : hypergraph.getVertices()) {
            vertex.setCost(Integer.MAX_VALUE);
        }
        for (Edge edge : hypergraph.getEdges()) {
            edge.setKj(0);
        }
        source.setCost(0);
        queue.add(source);
        while (queue.size() > 0) {
            Vertex u = queue.poll(); //Retrieves and removes first element
            for (Edge edge : u.getOutgoing_edges()) { // FS(u) må være u's outgoing edges
                edge.setKj(edge.getKj()+1); //legal?
                if (edge.getKj() == edge.getTail().size()) {
                    int f = 0; // Some cost function
                    Vertex y = edge.getHead();
                    if (!queue.contains(y)){ // if queue doesn't contain head of current edge
                        queue.add(y);
                    }
                    y.setCost(edge.getCost() + f);
                    // Predecessor of y should be updated
                }
            }
        }
        return hypergraph;
    }
}
