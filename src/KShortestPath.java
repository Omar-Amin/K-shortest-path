import java.util.*;

public class KShortestPath {
    private final Graph H;
    private ArrayList<Object[]> paths;

    public KShortestPath(Graph H)  {
        this.H = H;
    }

    public boolean run(int s, int t, int K, function toUse, boolean runUntilEmpty){
        this.paths = new ArrayList<>(K);
        SBT sbt = new SBT(H,toUse);
        minPQ L = new minPQ();
        ArrayList<HashMap<Integer,Integer>> deletedEdges = new ArrayList<>();
        HashMap<Integer,Integer> skip = new HashMap<>();
        skip.put(-1,0);
        ArrayList<Integer> pi = sbt.run(s,t,skip);
        if(pi == null) return false; //No path found
        Object[] pathObj = {pi,sbt.getPathCost(),0};
        L.insert(pathObj);
        deletedEdges.add(sbt.getDeleted());
        //NOTE: Each path returned has a cost for the path in the second last element, and the last
        // element is the index of where the deletedEdges is stored for that path in the arraylist
        int deletedPosition = 1;
        for (int k = 1; k <= K; k++) {
            if(L.size == 0){
                break;
            }
            pathObj = L.popMin();
            ArrayList<Integer> path = (ArrayList<Integer>) pathObj[0];
            HashMap<Integer,Integer> alreadyDeleted = deletedEdges.get((int) pathObj[2]);
            paths.add(new Object[] {path,pathObj[1]});
            if(k == K){
                break;
            }
            // size - 3 because the last element is index for hashmap and 2. last element is for cost
            for (HashMap<Integer,Integer> removed :backBranching(alreadyDeleted,path,(path.size()-1) - alreadyDeleted.get(-1))) {
                pi = sbt.run(s, t, removed);
                if(pi != null){
                    Object[] newPathObj = {pi,sbt.getPathCost(),deletedPosition++};
                    L.insert(newPathObj);
                    deletedEdges.add(removed);
                }
            }
        }

        return true;
    }

    private ArrayList<HashMap<Integer,Integer>> backBranching(HashMap<Integer,Integer> alreadyDeletedEdges, ArrayList<Integer> hyperpath, int startFrom) {
        ArrayList<HashMap<Integer,Integer>> setOfHypergraphs = new ArrayList<>();
        int counter = (hyperpath.size()-1)-startFrom;
        for (int i = startFrom; i >= 0; i--) {
            HashMap<Integer,Integer> edgesRemoved = new HashMap<>(alreadyDeletedEdges);
            int edge = hyperpath.get(i);
            edgesRemoved.put(edge,1);
            // starting for size-3 because the last two elements are edges in the path but indexes of something else (explained above)
            for (int j = startFrom; j >= i+1 ; j--) {
                int fixedEdge = hyperpath.get(j);
                int head = H.head(hyperpath.get(j));
                for (int e : H.BS(head)){
                    if(e != fixedEdge){
                        edgesRemoved.put(e,1);
                    }
                }
            }

            edgesRemoved.put(-1,counter);
            setOfHypergraphs.add(edgesRemoved);
            counter++;
        }

        return setOfHypergraphs;
    }

    public ArrayList<Object[]> getPaths(){
        return paths;
    }

}