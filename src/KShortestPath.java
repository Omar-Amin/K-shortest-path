import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class KShortestPath {

    private final ArrayList<Hyperpath> paths;

    public KShortestPath(Hypergraph H, Vertex s, Vertex t,int K)  {
        PriorityQueue<Hyperpath> L = new PriorityQueue<>(((o1, o2) -> Double.compare(o1.getCost(),o2.getCost())));
        ShortestPath sbt = new ShortestPath();
        L.add(sbt.SBT(H,s,t,new HashMap<>()));
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
            for (Pair<HashMap<Integer,Integer>,Integer> removed :backBranching(path.getDeletedEdges(),path.getPath(),path.getVersion())) {
                Hyperpath pi = sbt.SBT(H,s,t,removed.getKey());
                if(pi != null){
                    pi.setVersion(pi.getVersion()-removed.getValue());
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
    private ArrayList<Pair<HashMap<Integer,Integer>,Integer>> backBranching(HashMap<Integer,Integer> alreadyDeletedEdges, ArrayList<Edge> hyperpath, int startFrom) {
        ArrayList<Pair<HashMap<Integer,Integer>,Integer>> setOfHypergraphs = new ArrayList<>();
        int counter = 0;
        for (int i = startFrom; i >= 0; i--) {
            HashMap<Integer,Integer> edgesRemoved = new HashMap<>(alreadyDeletedEdges);

            Edge edge = hyperpath.get(i);
            edgesRemoved.put(edge.getId(),1);

            ArrayList<Edge> ingoingFromPath = new ArrayList<>();
            HashMap<Integer,Integer> edgesFromPath = new HashMap<>();

            for (int j = hyperpath.size()-1; j >= i+1 ; j--) {
                ingoingFromPath.addAll(hyperpath.get(j).getHead().getIngoing_edges());
                edgesFromPath.put(hyperpath.get(j).getId(),1);
            }

            for (Edge e :ingoingFromPath) {
                if(edgesFromPath.get(e.getId()) == null){
                    edgesRemoved.put(e.getId(),1);
                }
            }

            // DEBUG
/*            System.out.println("ITERATION: " + i);
            for (Edge e :edgesRemoved) {
                System.out.println(e.getId()+1);
            }
            System.out.println("____________");*/
            // DEBUG

            setOfHypergraphs.add(new Pair<>(edgesRemoved,counter));
            counter++;
        }

        return setOfHypergraphs;
    }

    public ArrayList<Hyperpath> getPaths(){
        return paths;
    }

}