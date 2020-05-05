import java.util.*;

public class SBT {
    private minPQ PQ;
    private ArrayList<Integer> path;
    Graph g;
    int[] predecessor;
    WeightingFunctions costFunction;

    public SBT(Graph g, function toUse){
        this.PQ = new minPQ();
        this.path = new ArrayList<>();
        this.g = g;
        costFunction = new WeightingFunctions(g,toUse);
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
            if(costFunction.functionType != function.min) {
                if (vertex[0] == target) {
                    getPath(source, target);
                    return path;
                }
            }
            for (int edge:g.FS(vertex[0])) {
                if(contains(edge,skip)) continue;
                kj[edge]++;
                if(kj[edge] == g.tail(edge).length){
                    int f = this.costFunction.run(edge);
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

    public void getPath(int source,int target){
        HashMap<Integer, Integer> in = edgesInPath(source,target);

        PriorityQueue<Integer> zeroIn = new PriorityQueue<>();
        zeroIn.add(target);
        path.add(this.predecessor[target]);
        while (!zeroIn.isEmpty()){
            int vertex = zeroIn.poll();
            if (vertex == source){
                continue;
            }
            for (int tail : g.tail(this.predecessor[vertex])) {
                // decrease each value for the vertex if the edge polled out
                // is a part of its outgoing edge
                in.put(tail,in.get(tail) - 1);
                if(in.get(tail) <= 0 && tail != source){
                    zeroIn.add(tail);
                    path.add(this.predecessor[tail]);
                }
            }
        }
    }

    private HashMap<Integer, Integer> edgesInPath(int source, int target){
        HashMap<Integer, Integer> in = new HashMap<>();
        HashMap<Integer, Integer> edges = new HashMap<>();

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(target);
        while (!stack.isEmpty()){
            target = stack.pop();
            if(source == target) continue;
            int edge = this.predecessor[target];
            if(edges.get(edge) != null) continue;
            edges.put(edge,1);
            for (int v:g.tail(edge)){
                in.putIfAbsent(v,0);
                in.put(v,in.get(v)+1);
                stack.add(v);
            }

        }

        return in;
    }

    public boolean contains(int edge, int[] skip){
        for (int edgeSkip:skip)
            if(edge == edgeSkip) return true;
        return false;
    }
}
