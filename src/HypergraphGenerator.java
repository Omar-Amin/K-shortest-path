import java.util.ArrayList;

public class HypergraphGenerator {

    public HypergraphGenerator(int[][] metaGraph,int maxNodes,int minNodes , int tailSize, int maxCost){
        Hypergraph hg = new Hypergraph().generateRandomHypergraph(maxNodes,minNodes,tailSize,maxCost);
        ArrayList<Hypergraph> hyperEdges = new ArrayList<>();
        hyperEdges.add(hg);

        for (int i = 1; i < metaGraph[0].length; i++) {
            Hypergraph hypergraph = new Hypergraph();
            hypergraph.setAmountOfVertices(hyperEdges.get(i-1).getAmountOfVertices());
            hypergraph.setAmountOfEdges(hyperEdges.get(i-1).getAmountOfEdges());
            hypergraph.generateRandomHypergraph(maxNodes,minNodes , tailSize, maxCost);
            hyperEdges.add(hypergraph);
        }

    }
}
