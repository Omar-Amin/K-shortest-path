import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

public class ShortestPath {
    //Hypergraph er det samme som hyperpath
    //Class attributes
    private PriorityQueue<Vertex> pq = new PriorityQueue<>((o1, o2) -> o1.getCost() - o2.getCost());
    private ArrayList<Edge> path = new ArrayList<>();

    public void nothing(){
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
        for (Edge edge : hypergraph.getEdges()) {
            edge.setKj(0);
        }
        source.setCost(0);
        pq.add(source);
        while (pq.size() > 0) {
/*            Object[] temp2 = pq.toArray();
            for (Object v:temp2) {
                Vertex ts = (Vertex) v;
                System.out.println(ts.getId()+1);
                System.out.println("COST: " + ts.getCost());
            }
            System.out.println("_____");*/
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
            edgeTailCost += v.getCost();
        }

        return edgeCost + edgeTailCost;
    }

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

}
