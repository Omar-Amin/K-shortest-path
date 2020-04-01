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

            for (int j = edgesFromPath.size()-1; j >= i+1 ; j--) {
                fixEdge(newHypergraph,hyperpathClone.getEdges().get(j).getHead());
            }
            // DEBUG
            //debugEdges(newHypergraph);
            //debugVertices(newHypergraph);
            // DEBUG
            setOfHypergraphs.add(newHypergraph);
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
        ArrayList<Integer> vertexEdges = new ArrayList<>();
        for (Edge e :vertex.getIngoing_edges()) {
            vertexEdges.add(e.getId());
        }
        ArrayList<Integer> hyperEdges = new ArrayList<>();
        // need to make this piece much prettier, just a temporary working solution
        for (Vertex value : vertices) {
            if (value.getId() == vertex.getId()) {
                for (Edge e :value.getIngoing_edges()) {
                    hyperEdges.add(e.getId());
                }
                ArrayList<Integer> difference = new ArrayList<>();
                for (Integer i :hyperEdges) {
                    if(!vertexEdges.contains(i)){
                        difference.add(i);
                    }
                }
                for (Integer i :difference) {
                    for (Edge e :hypergraph.getEdges()) {
                        if(e.getId() == i){
                            for (Vertex v :e.getTail()) {
                                for (int j = 0; j < v.getOutgoing_edges().size(); j++) {
                                    if(v.getOutgoing_edges().get(j).getId() == i){
                                        v.getOutgoing_edges().remove(j);
                                        break;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                value.setIngoing_edges(vertex.getIngoing_edges());
                break;
            }
        }

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