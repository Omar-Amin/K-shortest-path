import java.util.ArrayList;

public class Dijkstra {
    private minPQ PQ;
    private ArrayList<Integer> path;
    Graph g;
    int[] predecessor;

    public Dijkstra(Graph g){
        this.g = g;
        PQ = new minPQ();
        path = new ArrayList<>();
        this.predecessor = new int[g.vertexLookup.length];
    }

    public ArrayList<Integer> run(int source, int target){
        for (int i = 0; i < g.vertexLookup.length; i++) {
            if(i == source) g.setVertexCost(i,0);
            else g.setVertexCost(i, Integer.MAX_VALUE);
        }
        int[] s = {source,0};
        PQ.insert(s);

        while(PQ.size > 0){
            int[] u = PQ.popMin();
            if(u[0] == target){
                getPath(source,target);
                return path;
            }
            for (int e:g.FS(u[0])) {
                int v = g.head(e);
                int weight = g.getVertexCost(u[0])+g.getEdgeCost(e);
                if(weight < g.getVertexCost(v)){
                    if(!PQ.contains(v)){
                        int[] insert = {v,weight};
                        PQ.insert(insert);
                    } else {
                        PQ.decreaseValue(v,weight);
                    }
                    this.predecessor[v] = e;
                    g.setVertexCost(v,weight);
                }
            }
        }
        return null;
    }

    public void getPath(int source,int vertex){
        if(source == vertex) return;
        int edge = this.predecessor[vertex];
        int[] tail = g.tail(edge);
        for (int v:g.tail(edge)){
            getPath(source,v);
        }
        if(!path.contains(edge)) path.add(edge);
    }
}
