import java.util.ArrayList;
import java.util.List;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();
    private int shortestDistance = 0;

    public Graph(){

    }

    public Graph transformToGraph(Hypergraph hypergraph,int source, int target){
        ArrayList<Vertex> vertices = hypergraph.getVertices();
        ArrayList<Edge> edges = hypergraph.getEdges();
        for (int i = 0; i < vertices.size(); i++) {
            nodes.add(null);
        }

        for (Vertex v :vertices) {
            Node n = new Node(v.getId());
            nodes.set(v.getId(),n);
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
        dijkstra.computePaths(nodes.get(source));
        List<Node> path = dijkstra.getShortestPathTo(nodes.get(target));
        //System.out.println("Path: " + path);
        for(Node n: path){
            if(n.shortestDistance > shortestDistance){
                shortestDistance = (int) n.shortestDistance;
            }
        }

        return this;
    }

    public int getShortestDistance(){
        return shortestDistance;
    }

}
