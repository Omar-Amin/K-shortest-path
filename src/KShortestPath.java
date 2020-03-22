import java.util.ArrayList;

public class KShortestPath {

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K){
        // we should use "pair" class for our priority queue
        ShortestPath sp = new ShortestPath(H,H.getVertices().get(0),H.getVertices().get(H.getVertices().size()-1));
        ArrayList<Edge> hyperpath = sp.getShortestPath();

        backBranching(H,hyperpath);

    }

    public void tempMethod(){

    }

    private ArrayList<Hypergraph> backBranching(Hypergraph hypergraph, ArrayList<Edge> hyperpath){
        ArrayList<Hypergraph> setOfHypergraphs = new ArrayList<>();

        for (int i = hyperpath.size()-1; i >= 0; i--) {
            // we have to clone the edges, otherwise it will change the edges in our hypergraph
            hypergraph.getEdges().remove(hyperpath.get(i));

            Hypergraph newHypergraph = new Hypergraph().edgesInput((ArrayList<Edge>) hypergraph.getEdges().clone());
            // we can just remove it since it is the same object

            for (int j = hyperpath.size()-1; j >= i+1 ; j--) {
                newHypergraph.getEdges().add(hyperpath.get(j));
            }
            // I DONT UNDERSTAND ROJINS EXAMPLE, HOW ARE EDGES 7 AND 5 REMOVED?!?!?
            // DEBUG
            for (Edge edge :newHypergraph.getEdges()) {
                System.out.println("Edge id: " + (edge.getId()+1));
                System.out.println("Cost of edge: " + edge.getCost());
            }
            System.out.println("_______");
            // DEBUG
        }

        return setOfHypergraphs;
    }


}
