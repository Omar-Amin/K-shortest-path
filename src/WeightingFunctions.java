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
        for (int vertex: graph.tail(edge)) {
            sum += graph.getVertexCost(vertex);
        }
        return sum;
    }
    private double distanceFunction(int edge){
        double max = -1;
        for (int vertex: graph.tail(edge)) {
            double vertexPrice = graph.getVertexCost(vertex);
            if(max < vertexPrice || max == -1) max = vertexPrice;
        }
        return max + graph.getEdgeCost(edge);
    }
    private double minFunction(int edge){
        double min = -1;
        for (int vertex: graph.tail(edge)) {
            double vertexPrice = graph.getVertexCost(vertex);
            if(min > vertexPrice || min == -1) min = vertexPrice;
        }
        return min + graph.getEdgeCost(edge);
    }
}