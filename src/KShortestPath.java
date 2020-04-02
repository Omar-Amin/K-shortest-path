import java.util.ArrayList;

public class KShortestPath {

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K)  {
        // we should use "pair" class for our priority queue
        ShortestPath sp = new ShortestPath(H,H.getVertices().get(0),H.getVertices().get(H.getVertices().size()-1),new ArrayList<>());
        ArrayList<Edge> hp = sp.getShortestPath().getKey();
        System.out.println(sp.getShortestPath().getValue());

        ArrayList<ArrayList<Edge>> listOfRemovedEdges = backBranching(hp);

        ShortestPath sp2 = new ShortestPath(H,H.getVertices().get(0),H.getVertices().get(H.getVertices().size()-1),listOfRemovedEdges.get(0));
        System.out.println(sp2.getShortestPath().getValue());
    }

    public void tempMethod(){

    }

    private ArrayList<ArrayList<Edge>> backBranching(ArrayList<Edge> hyperpath) {
        ArrayList<ArrayList<Edge>> setOfHypergraphs = new ArrayList<>();

        for (int i = hyperpath.size()-1; i >= 0; i--) {
            ArrayList<Edge> edgesRemoved = new ArrayList<>();
            Edge edge = hyperpath.get(i);
            edgesRemoved.add(edge);

            ArrayList<Edge> ingoingFromPath = new ArrayList<>();
            ArrayList<Edge> edgesFromPath = new ArrayList<>();

            for (int j = hyperpath.size()-1; j >= i+1 ; j--) {
                ingoingFromPath.addAll(hyperpath.get(j).getHead().getIngoing_edges());
                edgesFromPath.add(hyperpath.get(j));
            }

            for (Edge e :ingoingFromPath) {
                if(!edgesFromPath.contains(e)){
                    edgesRemoved.add(e);
                }
            }
            setOfHypergraphs.add(edgesRemoved);
        }

        return setOfHypergraphs;
    }

}