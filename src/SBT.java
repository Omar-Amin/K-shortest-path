import java.util.*;

public class SBT {
    private final Graph g;
    private int[] predecessor;
    private final WeightingFunctions costFunction;
    private HashMap<Integer,Integer> deleted;
    private int deletedPosition = 0;
    private double pathCost;

    public SBT(Graph g, function toUse){
        this.g = g;
        costFunction = new WeightingFunctions(g,toUse);
    }

    public ArrayList<Integer> run(int source, int target, HashMap<Integer,Integer> skip){
        minPQ PQ = new minPQ();
        deleted = skip;
        int[] kj = new int[g.edgeLookup.length];
        this.predecessor = new int[g.vertexLookup.length];
        for(int i = 0; i < g.vertexLookup.length;i++){
            g.setVertexCost(i,Double.MAX_VALUE);
        }
        Object[] vertex = {source,0};
        g.setVertexCost(source,0);
        PQ.insert(vertex);
        while(PQ.size > 0){
            vertex = PQ.popMin();
            if((int) vertex[0] == target && costFunction.functionType != function.min){
                return getPath(source, target);
            }
            for (int edge:g.FS((int) vertex[0])) {
                if(skip.get(edge) != null) continue;
                kj[edge]++;
                if(kj[edge] == g.tail(edge).length){
                    double f = this.costFunction.run(edge);
                    int y = g.head(edge);
                    if (g.getVertexCost(y) > f){
                        if(!PQ.contains(y)){
                            if(g.getVertexCost(y) < Double.MAX_VALUE){
                                for (int tail:g.FS(y)) {
                                    kj[tail]--;
                                }
                            }
                            Number[] head = {y,f};
                            PQ.insert(head);
                        } else {
                            PQ.decreaseValue(y,f);
                        }
                        g.setVertexCost(y,f);
                        this.predecessor[y] = edge;
                    }
                }
            }
        }
        if(g.getVertexCost(g.vertexLookup.length-1) != Double.MAX_VALUE){
            return getPath(source,target);
        }
        return null;
    }

    public ArrayList<Integer> getPath(int source,int target){
        HashMap<Integer, Integer> in = edgesInPath(target);
        ArrayList<Integer> path = new ArrayList<>();
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
        Collections.reverse(path);
        this.pathCost = g.getVertexCost(target);
        path.add(deletedPosition++);
        return path;
    }

    private HashMap<Integer, Integer> edgesInPath(int target){
        HashMap<Integer, Integer> in = new HashMap<>();
        HashMap<Integer, Integer> edges = new HashMap<>();

        ArrayDeque<Integer> stack = new ArrayDeque<>();
        stack.push(target);
        while (!stack.isEmpty()){
            target = stack.pop();
            int edge = this.predecessor[target];
            if(edges.get(edge) != null) continue;
            edges.put(edge,0);
            for (int v:g.tail(edge)){
                in.putIfAbsent(v,0);
                in.put(v,in.get(v)+1);
                stack.add(v);
            }

        }

        return in;
    }

    public HashMap<Integer,Integer> getDeleted(){
        return deleted;
    }

    public double getPathCost() {
        return pathCost;
    }
}