enum function{
    sum,
    distance,
    min
}

public class WeightingFunctions {
    Graph graph;
    function functionType;

    public WeightingFunctions(Graph graph,function toUse){
        this.graph = graph;
        this.functionType = toUse;
    }

    public double run(int edge){
        if(functionType == function.sum){
            return sumFunction(edge);
        } else if (functionType == function.distance) {
            return distanceFunction(edge);
        } else if (functionType == function.min){
            return minFunction(edge);
        }
        return -1;
    }

    private double sumFunction(int edge){
        double sum = graph.getEdgeCost(edge);
        int[] index = graph.tail(edge);
        for (int i = 0; i < index[1]; i++) {
            sum += graph.getVertexCost(graph.edgeTable[index[0]+i]);
        }
        return sum;
    }
    private double distanceFunction(int edge){
        double max = -1;
        int[] index = graph.tail(edge);
        for (int i = 0; i < index[1]; i++) {
            double vertexPrice = graph.getVertexCost(graph.edgeTable[index[0]+i]);
            if(max < vertexPrice || max == -1) max = vertexPrice;
        }
        return max + graph.getEdgeCost(edge);
    }
    private double minFunction(int edge){
        double min = -1;
        int[] index = graph.tail(edge);
        for (int i = 0; i < index[1]; i++) {
            double vertexPrice = graph.getVertexCost(graph.edgeTable[index[0]+i]);
            if(min > vertexPrice || min == -1) min = vertexPrice;
        }
        return min + graph.getEdgeCost(edge);
    }
}