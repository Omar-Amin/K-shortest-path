import java.util.ArrayList;

public class KShortestPath {

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K) throws CloneNotSupportedException {
        // we should use "pair" class for our priority queue
        ShortestPath sp = new ShortestPath(H,H.getVertices().get(0),H.getVertices().get(H.getVertices().size()-1));
        ArrayList<Edge> hp = sp.getShortestPath();
        Hypergraph hyperpath = new Hypergraph().edgesInput(hp);

        backBranching(H,hyperpath);
    }

    public void tempMethod(){

    }

    private ArrayList<Hypergraph> backBranching(Hypergraph hypergraph, Hypergraph hyperpath) throws CloneNotSupportedException {
        ArrayList<Hypergraph> setOfHypergraphs = new ArrayList<>();
        ArrayList<Edge> edgesFromPath = (ArrayList<Edge>) hyperpath.getEdges().clone();
        ArrayList<Vertex> verticesFromPath = (ArrayList<Vertex>) hyperpath.getVertices().clone();
        ArrayList<Edge> edgesFromGraph = (ArrayList<Edge>) hypergraph.getEdges().clone();
        for (int i = edgesFromPath.size()-1; i >= 0; i--) {
            System.out.println(verticesFromPath.get(0).getOutgoing_edges().size());

            // we have to clone the edges, otherwise it will change the edges in our hypergraph
            Hypergraph newHypergraph = new Hypergraph().edgesInput(edgesFromGraph);

            Edge edg = edgesFromPath.get(i);

            removeEdge(newHypergraph,edg);

            newHypergraph.getEdges().remove(edgesFromPath.get(i));

            // we can just remove it since it is the same object
            for (int j = edgesFromPath.size()-1; j >= i+1 ; j--) {
                fixEdge(newHypergraph,edgesFromPath.get(j).getHead());
            }
            // I DONT UNDERSTAND ROJINS EXAMPLE, HOW ARE EDGES 7 AND 5 REMOVED?!?!?
            // DEBUG
            //debugEdges(newHypergraph);
            //debugVertices(hypergraph);
            // DEBUG
        }

        return setOfHypergraphs;
    }

    private void removeEdge(Hypergraph hypergraph, Edge edge){
        for (Vertex vertice :edge.getTail()) {
            hypergraph.getVertices().get(vertice.getId()).getOutgoing_edges().remove(edge);
        }
        hypergraph.getVertices().get(edge.getHead().getId()).getIngoing_edges().remove(edge);
    }

    private void fixEdge(Hypergraph hypergraph, Vertex vertex){
        //hypergraph.getVertices().get(vertex.getId());
        //System.out.println("Vertex ID: " + (vertex.getId()+1));
        //System.out.println("Vertex size: " + vertex.getIngoing_edges().size());
    }

    private void debugEdges(Hypergraph hypergraph){
        for (Edge edge :hypergraph.getEdges()) {
            System.out.println("Edge id: " + (edge.getId()+1));
        }
        System.out.println("_______");
    }

    private void debugVertices(Hypergraph hypergraph){
        System.out.println("ITEATION START ");
        System.out.println();
        for (Vertex vertex :hypergraph.getVertices()) {
            System.out.println("Vertex id: " + (vertex.getId()+1));
            if(vertex.getOutgoing_edges() != null){
                for (Edge edge: vertex.getOutgoing_edges()) {
                    System.out.println("Outgoing id: " + (edge.getId()+1));
                }
            }
            if(vertex.getIngoing_edges() != null){
                for (Edge edge: vertex.getIngoing_edges()) {
                    System.out.println("Ingoing id: " + (edge.getId()+1));
                }
            }
            System.out.println("_______");
        }
        System.out.println();
        System.out.println("ITEATION END");
    }

}
