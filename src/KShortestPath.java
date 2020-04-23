import javafx.util.Pair;

import java.util.ArrayList;

public class KShortestPath {

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K)  {
        ShortestPath sp = new ShortestPath();
        Pair<ArrayList<Edge>, Double> pair = sp.SBT(H,s,t,new ArrayList<>());
        ArrayList<Edge> hp = pair.getKey();
        System.out.println(pair.getValue());

        ArrayList<ArrayList<Edge>> listOfRemovedEdges = backBranching(new ArrayList<>(),hp);

        ShortestPath sp2 = new ShortestPath();
        Pair<ArrayList<Edge>, Double> pair2 = sp2.SBT(H,s,t,listOfRemovedEdges.get(0));

        listOfRemovedEdges = backBranching(listOfRemovedEdges.get(0),pair2.getKey());

    }

    /**
     * This method makes lists of edges that should be deleted by using a method called back-branching.
     *
     * @param alreadyDeletedEdges: Edges that have already been deleted.
     * */
    private ArrayList<ArrayList<Edge>> backBranching(ArrayList<Edge> alreadyDeletedEdges, ArrayList<Edge> hyperpath) {
        ArrayList<ArrayList<Edge>> setOfHypergraphs = new ArrayList<>();

        for (int i = hyperpath.size()-1; i >= 0; i--) {
            ArrayList<Edge> edgesRemoved = new ArrayList<>(alreadyDeletedEdges);

            Edge edge = hyperpath.get(i);
            edgesRemoved.add(edge);

            ArrayList<Edge> ingoingFromPath = new ArrayList<>();
            ArrayList<Edge> edgesFromPath = new ArrayList<>();

            for (int j = hyperpath.size()-1; j >= i+1 ; j--) {
                ingoingFromPath.addAll(hyperpath.get(j).getHead().getIngoing_edges());
                edgesFromPath.add(hyperpath.get(j));
            }

            for (Edge e :ingoingFromPath) {
                if(!edgesFromPath.contains(e) && !edgesRemoved.contains(e)){
                    edgesRemoved.add(e);
                }
            }

            // DEBUG
/*            System.out.println("ITERATION: " + i);
            for (Edge e :edgesRemoved) {
                System.out.println(e.getId()+1);
            }
            System.out.println("____________");*/
            // DEBUG

            setOfHypergraphs.add(edgesRemoved);
        }

        return setOfHypergraphs;
    }

}