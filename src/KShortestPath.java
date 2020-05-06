import java.util.*;

public class KShortestPath {
    private final Graph H;
    private final ArrayList<ArrayList<Integer>> paths;

    public KShortestPath(Graph H, int s, int t, int K, function toUse, boolean runUntilEmpty)  {
        this.H = H;
        paths = new ArrayList<>();
        SBT sbt = new SBT(H,toUse);
        PriorityQueue<ArrayList<Integer>> L = new PriorityQueue<>((Comparator.comparingInt(o -> o.get(o.size() - 2))));
        ArrayList<HashMap<Integer,Integer>> deletedEdges = new ArrayList<>();
        HashMap<Integer,Integer> skip = new HashMap<>();
        skip.put(-1,0);
        L.add(sbt.run(s,t,skip));
        deletedEdges.add(sbt.getDeleted());
        //NOTE: Each path returned has a cost for the path in the second last element, and the last
        // element is the index of where the deletedEdges is stored for that path in the arraylist

        for (int k = 1; k <= K; k++) {
            if(L.isEmpty()){
                break;
            }
            ArrayList<Integer> path = L.poll();
            paths.add(path);
            if(k == K){
                break;
            }
            HashMap<Integer,Integer> alreadyDeleted = deletedEdges.get(path.get(path.size()-1));
            // size - 3 because the last element is index for hashmap and 2. last element is for cost
            for (HashMap<Integer,Integer> removed :backBranching(alreadyDeleted,path,(path.size()-3) - alreadyDeleted.get(-1))) {
                ArrayList<Integer> pi = sbt.run(s,t,removed);
                if(pi != null){
                    L.add(pi);
                    deletedEdges.add(sbt.getDeleted());
                }
            }
        }

    }

    private ArrayList<HashMap<Integer,Integer>> backBranching(HashMap<Integer,Integer> alreadyDeletedEdges, ArrayList<Integer> hyperpath, int startFrom) {
        ArrayList<HashMap<Integer,Integer>> setOfHypergraphs = new ArrayList<>();
        int counter = 0;
        for (int i = startFrom; i >= 0; i--) {
            HashMap<Integer,Integer> edgesRemoved = new HashMap<>(alreadyDeletedEdges);

            int edge = hyperpath.get(i);
            edgesRemoved.put(edge,1);

            ArrayList<Integer> ingoingFromPath = new ArrayList<>();
            HashMap<Integer,Integer> edgesFromPath = new HashMap<>();

            // starting for size-3 because the last two elements are edges in the path but indexes of something else (explained above)
            for (int j = hyperpath.size()-3; j >= i+1 ; j--) {
                int head = H.head(hyperpath.get(j));
                for (int e : H.BS(head)){
                    ingoingFromPath.add(e);
                }
                edgesFromPath.put(hyperpath.get(j),1);
            }

            // fix the edge by adding the deleted edges
            // if it is not in edgesFromPath it adds it as deleted edge
            for (int e :ingoingFromPath) {
                if(edgesFromPath.get(e) == null){
                    edgesRemoved.put(e,1);
                }
            }

            edgesRemoved.put(-1,counter);
            setOfHypergraphs.add(edgesRemoved);
            counter++;
        }

        return setOfHypergraphs;
    }

    public ArrayList<ArrayList<Integer>> getPaths(){
        return paths;
    }

}