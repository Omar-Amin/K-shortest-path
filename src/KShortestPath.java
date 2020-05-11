import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class KShortestPath {

    private final ArrayList<Hyperpath> paths;

    /**
     * This is the algorithm running for finding the k-shortest graph.
     *
     * @param runUntilEmpty: True if you want to run the code until the queue is empty, meaning
     *                       that we have calculated the weight for all nodes
     * @param H: Hypergraph that you want to run K-shortestpath
     * @param toUse: Which weighting function you want to use
     * */
    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K, function toUse, boolean runUntilEmpty)  {
        PriorityQueue<Hyperpath> L = new PriorityQueue<>((Comparator.comparingDouble(Hyperpath::getCost)));
        ShortestPath sbt = new ShortestPath(toUse);
        L.add(sbt.SBT(H,s,t,new HashMap<>(),runUntilEmpty));
        paths = new ArrayList<>();

        for (int k = 1; k <= K; k++) {
            if(L.isEmpty()){
                break;
            }
            Hyperpath path = L.poll();
            paths.add(path);
            if(k == K){
                break;
            }
            for (HashMap<Integer,Integer> removed :backBranching(path.getDeletedEdges(),path.getPath(),path.getVersion())) {
                Hyperpath pi = sbt.SBT(H,s,t,removed,runUntilEmpty);
                if(pi != null){
                    // the version is stored in key -1
                    pi.setVersion(pi.getVersion()-removed.get(-1));
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
    private ArrayList<HashMap<Integer,Integer>> backBranching(HashMap<Integer,Integer> alreadyDeletedEdges, ArrayList<Edge> hyperpath, int startFrom) {
        ArrayList<HashMap<Integer,Integer>> setOfHypergraphs = new ArrayList<>();
        // counter in order to know which version to start from
        int counter = (hyperpath.size()-1)-startFrom;
        for (int i = startFrom; i >= 0; i--) {
            HashMap<Integer,Integer> edgesRemoved = new HashMap<>(alreadyDeletedEdges);

            Edge edge = hyperpath.get(i);
            edgesRemoved.put(edge.getId(),1);

            ArrayList<Edge> ingoingFromPath = new ArrayList<>();
            HashMap<Integer,Integer> edgesFromPath = new HashMap<>();

            // back-fixing the edge
            for (int j = startFrom; j >= i+1 ; j--) {
                ingoingFromPath.addAll(hyperpath.get(j).getHead().getIngoing_edges());
                edgesFromPath.put(hyperpath.get(j).getId(),1);
            }

            // adding all of the deleted edges (from back-fixing)
            for (Edge e :ingoingFromPath) {
                if(edgesFromPath.get(e.getId()) == null){
                    edgesRemoved.put(e.getId(),1);
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