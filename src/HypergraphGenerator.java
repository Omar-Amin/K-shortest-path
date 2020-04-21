import java.util.ArrayList;

public class HypergraphGenerator {

    private final Hypergraph hypergraph;

    public HypergraphGenerator(int[][] metaGraph,int maxNodes,int minNodes , int tailSize, int maxCost, int seed){
        Hypergraph hg = new Hypergraph().generateRandomHypergraph(maxNodes,minNodes,tailSize,maxCost, seed);
        ArrayList<Hypergraph> hyperEdges = new ArrayList<>();
        hyperEdges.add(hg);

        for (int i = 1; i < metaGraph[0].length; i++) {
            Hypergraph hypergraph = new Hypergraph();
            hypergraph.setAmountOfVertices(hyperEdges.get(i-1).getAmountOfVertices());
            hypergraph.setAmountOfEdges(hyperEdges.get(i-1).getAmountOfEdges());
            hypergraph.generateRandomHypergraph(maxNodes,minNodes , tailSize, maxCost, seed+i);
            hyperEdges.add(hypergraph);
        }

        Hypergraph h2 = new Hypergraph();
        h2.setAmountOfEdges(hyperEdges.get(hyperEdges.size()-1).getAmountOfEdges());
        h2.setAmountOfVertices(hyperEdges.get(hyperEdges.size()-1).getAmountOfVertices());
        this.hypergraph = h2.connectHyperedges(metaGraph,hyperEdges);
    }

    public Hypergraph getHypergraph() {
        return hypergraph;
    }
}
