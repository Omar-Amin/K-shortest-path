import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class KShortestPath {
    private final Hypergraph H;
    private final ArrayList<Hyperpath> paths;

    public KShortestPath(Hypergraph H)  {
        this.H = H;
        this.paths = new ArrayList<>();
    }

    /**
     * This is the algorithm running for finding the k-shortest graph.
     *
     * @param runUntilEmpty: True if you want to run the code until the queue is empty, meaning
     *                       that we have calculated the weight for all nodes
     * @param toUse: Which weighting function you want to use
     * */
    public boolean run(Vertex s, Vertex t,int K, function toUse, boolean runUntilEmpty){
        ShortestPath sbt = new ShortestPath(toUse);
        minPQ L = new minPQ();
        Hyperpath first = sbt.SBT(H,s,t,new HashMap<>(),runUntilEmpty);
        if(first == null) return false; //No path exists
        Object[] newPi = {first,first.getCost()};
        L.insert(newPi);
        for (int k = 1; k <= K; k++) {
            if(L.size == 0){
                break;
            }
            Hyperpath path = (Hyperpath) L.popMin()[0];
            paths.add(path);
            if(k == K){
                break;
            }
            for (HashMap<Integer,Integer> removed :backBranching(path.getDeletedEdges(),path.getPath(),path.getVersion())) {
                Hyperpath pi = sbt.SBT(H,s,t,removed,runUntilEmpty);
                if(pi != null){
                    // the version is stored in key -1
                    pi.setVersion(pi.getVersion()-removed.get(-1));
                    Object[] pi2 = {pi,pi.getCost()};
                    L.insert(pi2);
                }
            }
        }
        return true;
    }

    /**
     * This method makes lists of edges that should be deleted by using a method called back-branching.
     *
     * @param alreadyDeletedEdges: Edges that have already been deleted.
     * */
    private ArrayList<HashMap<Integer,Integer>> backBranching(HashMap<Integer,Integer> alreadyDeletedEdges, ArrayList<Edge> hyperpath, int startFrom) {
        ArrayList<HashMap<Integer,Integer>> setOfHypergraphs = new ArrayList<>();
        // counter in order to know which version to start from
        int counter = (hyperpath.size()-1)-startFrom;
        for (int i = startFrom; i >= 0; i--) {
            HashMap<Integer,Integer> edgesRemoved = new HashMap<>(alreadyDeletedEdges);

            Edge edge = hyperpath.get(i);
            edgesRemoved.put(edge.getId(),1);

            // back-fixing the edge
            for (int j = startFrom; j >= i+1 ; j--) {
                Edge fixedEdge = hyperpath.get(j);
                for (Edge e :fixedEdge.getHead().getIngoing_edges()) {
                    if(e != fixedEdge){
                        edgesRemoved.put(e.getId(),1);
                    }
                }
            }

            edgesRemoved.put(-1,counter);
            setOfHypergraphs.add(edgesRemoved);
            counter++;
        }

        return setOfHypergraphs;
    }

    public ArrayList<Hyperpath> getPaths(){
        return paths;
    }

}