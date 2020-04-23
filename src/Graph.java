import java.util.ArrayList;
import java.util.List;

public class Graph {

    private final ArrayList<Node> nodes = new ArrayList<>();
    private double shortestDistance = 0;
    private int getPathSize;

    public Graph(){

    }

    public Graph transformToGraph(Hypergraph hypergraph){
        ArrayList<Vertex> vertices = hypergraph.getVertices();
        ArrayList<Edge> edges = hypergraph.getEdges();
        for (int i = 0; i < hypergraph.getAmountOfVertices(); i++) {
            nodes.add(null);
        }

        for (Vertex v :vertices) {
            Node n = new Node(v.getId());
            nodes.set(v.getId(),n);
        }

        for (Edge e :edges) {
            Vertex v = e.getHead();
            for (Vertex v2 :e.getTail()) {
                nodes.get(v2.getId()).adjacencies.add(new GEdge(nodes.get(v.getId()),e.getCost()));
            }
        }
        return this;
    }

    public void runDijkstra(int source, int target){
        Dijkstra dijkstra = new Dijkstra();
        dijkstra.computePaths(nodes.get(source));
        List<Node> path = dijkstra.getShortestPathTo(nodes.get(target));
        //System.out.println("Path: " + path);
        for(Node n: path){
            if(n.shortestDistance > shortestDistance){
                shortestDistance = n.shortestDistance;
            }
        }
        getPathSize = path.size();
    }

    public double getShortestDistance(){
        return shortestDistance;
    }

    public int getGetPathSize(){
        return getPathSize;
    }

}
