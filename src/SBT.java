import java.util.ArrayList;
import java.util.Arrays;

public class SBT {
    private minPQ PQ;
    private ArrayList<Integer> path;
    Graph g;
    int[] predecessor;
    WeightingFunctions function;

    public SBT(Graph g, function toUse){
        this.PQ = new minPQ();
        this.path = new ArrayList<>();
        this.g = g;
        function = new WeightingFunctions(g,toUse);
    }

    public ArrayList<Integer> run(int source, int target, int[] skip){
        int[] kj = new int[g.edgeLookup.length];
        this.predecessor = new int[g.vertexLookup.length];
        for(int i = 0; i < g.vertexLookup.length;i++){
            g.setVertexCost(i,Integer.MAX_VALUE);
        }
        int[] vertex = {source,0};
        g.setVertexCost(vertex[0],vertex[1]);
        PQ.insert(vertex);
        while(PQ.size > 0){
            vertex = PQ.popMin();
            if(vertex[0] == target){
                getPath(source, target);
                return path;
            }
            for (int edge:g.FS(vertex[0])) {
                if(contains(edge,skip)) continue;
                kj[edge]++;
                if(kj[edge] == g.tail(edge).length){
                    int f = this.function.run(edge);
                    int y = g.head(edge);
                    if (g.getVertexCost(y) > f){
                        if(!PQ.contains(y)){
                            int[] head = {y,f};
                            PQ.insert(head);
                            if(g.getVertexCost(y) < Integer.MAX_VALUE){
                                for (int tail:g.FS(y)) {
                                    kj[tail]--;
                                }
                            }
                        } else {
                            PQ.decreaseValue(y,f);
                        }
                        g.setVertexCost(y,f);
                        this.predecessor[y] = edge;
                    }
                }
            }
        }
        return null;
    }

    public void getPath(int source,int vertex){

        if(source == vertex) return;
        int edge = this.predecessor[vertex];
        if(path.contains(edge)) return;
        for (int v:g.tail(edge)){
            getPath(source,v);
        }
        path.add(edge);
    }

    public boolean contains(int edge, int[] skip){
        for (int edgeSkip:skip)
            if(edge == edgeSkip) return true;
        return false;
    }
}
