import javafx.util.Pair;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class KShortestPath {

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K)  {
        PriorityQueue<Hyperpath> L = new PriorityQueue<>(((o1, o2) -> Double.compare(o1.getCost(),o2.getCost())));
        ShortestPath sbt = new ShortestPath();
        L.add(sbt.SBT(H,s,t,new ArrayList<>()));
        ArrayList<Hyperpath> paths = new ArrayList<>();

        for (int k = 0; k < K; k++) {
            if(L.isEmpty()){
                break;
            }
            Hyperpath path = L.poll();
            paths.add(path);
            for (ArrayList<Edge> removed :backBranching(path.getDeletedEdges(),path.getPath())) {
                Hyperpath pi = sbt.SBT(H,s,t,removed);
                if(pi != null){
                    L.add(pi);
                }
            }
        }

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