import java.util.*;

public class SBT {
    private final Graph g;
    private int[] predecessor;
    private final WeightingFunctions costFunction;
    private HashMap<Integer,Integer> deleted;
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
            this.predecessor[i] = -1;
        }
        Object[] vertexObj = {source,0.0};
        g.setVertexCost(source,0.0);
        PQ.insert(vertexObj);
        while(PQ.size > 0){
            vertexObj = PQ.popMin();
            int vertex = (int) vertexObj[0];
            if(vertex == target && costFunction.functionType != function.min){
                return getPath(source, target);
            }
            int[] indexes = g.FS(vertex);
            for (int i = 0; i < indexes[1]; i++) {
                int edge = g.vertexTable[indexes[0]+i];
                if(skip.get(edge) != null) continue;
                kj[edge]++;
                if(kj[edge] == g.tail(edge)[1]){
                    double f = this.costFunction.run(edge);
                    int y = g.head(edge);
                    if (g.getVertexCost(y) > f){
                        if(!PQ.contains(y)){
                            if(g.getVertexCost(y) < Double.MAX_VALUE){
                                int[] yfs = g.FS(y);
                                for (int j = 0; j < yfs[1]; j++) {
                                    int tail = g.vertexTable[yfs[0]+i];
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
        minPQ zeroIn = new minPQ();
        Object[] obj = {target,(double) target};
        zeroIn.insert(obj);
        path.add(this.predecessor[target]);
        while (zeroIn.size != 0){
            int vertex = (int) zeroIn.popMin()[0];
            if (vertex == source){
                continue;
            }
            int[] index = g.tail(this.predecessor[vertex]);
            for (int i = 0; i < index[1]; i++) {
                int tail = g.edgeTable[index[0]+i];
                in.put(tail,in.get(tail) - 1);
                if(in.get(tail) <= 0 && tail != source){
                    zeroIn.insert(new Object[] {tail,(double) tail});
                    path.add(this.predecessor[tail]);
                }
            }
        }
        Collections.reverse(path);
        this.pathCost = g.getVertexCost(target);
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
            int[] index = g.tail(this.predecessor[target]);
            for (int i = 0; i < index[1]; i++) {
                int v = g.edgeTable[index[0]+i];
                in.putIfAbsent(v,0);
                in.put(v,in.get(v)+1);
                if(this.predecessor[v] != -1) {
                    stack.push(v);
                }

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