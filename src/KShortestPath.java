import java.util.ArrayList;

public class KShortestPath {

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K)  {
        // we should use "pair" class for our priority queue
        ShortestPath sp = new ShortestPath(H,H.getVertices().get(0),H.getVertices().get(H.getVertices().size()-1));
        ArrayList<Edge> hp = sp.getShortestPath();
        Hypergraph hyperpath = new Hypergraph().edgesInput(hp);

        backBranching(H,hyperpath);
    }

    public void tempMethod(){

    }

    private ArrayList<Hypergraph> backBranching(Hypergraph hypergraph, Hypergraph hyperpath) {
        ArrayList<Hypergraph> setOfHypergraphs = new ArrayList<>();
        ArrayList<Edge> edgesFromPath = hyperpath.getEdges();
        ArrayList<Edge> edgesFromGraph = hypergraph.getEdges();

        for (int i = edgesFromPath.size()-1; i >= 0; i--) {
            Hypergraph newHypergraph = new Hypergraph().edgesInput(edgesFromGraph);
            Hypergraph hyperpathClone = new Hypergraph().edgesInput(edgesFromPath);

            Edge edge = hyperpathClone.getEdges().get(i);

            removeEdge(newHypergraph,edge);

            // we can just remove it since it is the same object
            for (int j = edgesFromPath.size()-1; j >= i+2 ; j--) {
                newHypergraph.getVertices().get(hyperpathClone.getVertices().get(j).getId()).getIngoing_edges().clear();
            }
            for (int j = edgesFromPath.size()-1; j >= i+1 ; j--) {
                fixEdge(newHypergraph,hyperpathClone.getEdges().get(j).getHead());
            }
            // DEBUG
            //debugEdges(newHypergraph);
            //debugVertices(newHypergraph);
            // DEBUG
        }

        return setOfHypergraphs;
    }

    private void removeEdge(Hypergraph hypergraph, Edge edge){
        for (Vertex vertice :edge.getTail()) {
            ArrayList<Edge> outgoingEdges = hypergraph.getVertices().get(vertice.getId()).getOutgoing_edges();
            for (int i = 0; i < outgoingEdges.size(); i++) {
                if(outgoingEdges.get(i).getId() == edge.getId()){
                    outgoingEdges.remove(i);
                    break;
                }
            }
        }
        ArrayList<Edge> ingoing_edges = hypergraph.getVertices().get(edge.getHead().getId()).getIngoing_edges();
        for (int i = 0; i < ingoing_edges.size(); i++) {
            if(ingoing_edges.get(i).getId() == edge.getId()){
                ingoing_edges.remove(i);
                break;
            }
        }

        ArrayList<Edge> edges = hypergraph.getEdges();

        for (int i = 0; i < edges.size(); i++) {
            if(edges.get(i).getId() == edge.getId()){
                edges.remove(i);
                return;
            }
        }
    }

    private void fixEdge(Hypergraph hypergraph, Vertex vertex){
        ArrayList<Vertex> vertices = hypergraph.getVertices();
        //TODO: Problem: The outgoing edges are not change, only the ingoing, fix later
        vertices.get(vertex.getId()).setIngoing_edges(vertex.getIngoing_edges());

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
