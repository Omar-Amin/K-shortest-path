import java.util.ArrayList;
import java.util.List;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();

    public Graph(){

    }

    public Graph transformToGraph(Hypergraph hypergraph){
        ArrayList<Vertex> vertices = hypergraph.getVertices();
        ArrayList<Edge> edges = hypergraph.getEdges();
        for (Vertex v :vertices) {
            Node n = new Node(v.getId());
            nodes.add(n);
        }

        for (Edge e :edges) {
            Vertex v = e.getHead();
            Node n = nodes.get(v.getId());
            for (Vertex v2 :e.getTail()) {
                Node n1 = nodes.get(v2.getId());
                n1.adjacencies.add(new GEdge(n,e.getCost()));
            }
        }

        Dijkstra dijkstra = new Dijkstra();
        dijkstra.computePaths(nodes.get(0));
        List<Node> path = dijkstra.getShortestPathTo(nodes.get(nodes.size()-1));
        System.out.println("Path: " + path);

        for(Node n: path){
            System.out.println("Node: " + (n.getValue()+1));
            System.out.println("Distance to " +
                    n + ": " + n.shortestDistance);
        }
        return this;
    }

}
