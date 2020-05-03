enum function{
    sum,
    max,
    min
}

public class WeightingFunctions {

    function functionType;

    public WeightingFunctions(function toUse){
        this.functionType = toUse;
    }

    public double costFunction(Edge edge){
        if(functionType == function.max){
            return maxCostFunction(edge);
        } else if(functionType == function.min){
            return minCostFunction(edge);
        } else if(functionType == function.sum){
            return sumCostFunction(edge);
        }
        throw new IllegalArgumentException("This cost function doesn't exist");
    }

    /**
     * Minimum cost function.
     * */
    private double minCostFunction(Edge edge){
        double edgeCost = edge.getCost();
        double edgeTailCost = Integer.MAX_VALUE;
        for (Vertex v : edge.getTail()) {
            if(v.getCost() < edgeTailCost){
                edgeTailCost = v.getCost();
            }
        }
        return edgeCost + edgeTailCost;
    }

    /**
     * Maximum cost function.
     * */
    private double maxCostFunction(Edge edge){
        double edgeCost = edge.getCost();
        double edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            if(v.getCost() > edgeTailCost){
                edgeTailCost = v.getCost();
            }
        }
        return edgeCost + edgeTailCost;
    }

    /**
     * Function that takes in an edge and find the cost value for
     * the given edge, by getting the cost of all of its tail vertices
     * and adding its own cost.
     *
     * @param edge: The edge to find the cost
     * */
    private double sumCostFunction(Edge edge){
        double edgeCost = edge.getCost();
        double edgeTailCost = 0;
        for (Vertex v : edge.getTail()) {
            edgeTailCost += v.getCost();
        }
        return edgeCost + edgeTailCost;
    }

    public function getFunctionType(){
        return functionType;
    }
}
