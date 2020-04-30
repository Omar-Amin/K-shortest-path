import java.util.ArrayList;

public class HypergraphGenerator {

    public HypergraphGenerator(){
    }

    /**
     * From a given metagraph, every edge is turned into a random hyperpath (hypergraph).
            * We later connect the hyperpaths into a hypergraph that looks like the metagraph.
     */
    public Hypergraph generateFromMetagraph(int[][] metaGraph,int maxNodes,int minNodes , int tailSize, int maxCost, int seed){
        Hypergraph hg = new Hypergraph().generateRandomHyperpath(maxNodes,minNodes,tailSize,maxCost, seed);
        ArrayList<Hypergraph> hyperEdges = new ArrayList<>();
        hyperEdges.add(hg);

        for (int i = 1; i < metaGraph[0].length; i++) {
            Hypergraph hypergraph = new Hypergraph();
            hypergraph.setAmountOfVertices(hyperEdges.get(i-1).getAmountOfVertices());
            hypergraph.setAmountOfEdges(hyperEdges.get(i-1).getAmountOfEdges());
            hypergraph.generateRandomHyperpath(maxNodes,minNodes , tailSize, maxCost, seed+i);
            hyperEdges.add(hypergraph);
        }

        Hypergraph h2 = new Hypergraph();
        h2.setAmountOfEdges(hyperEdges.get(hyperEdges.size()-1).getAmountOfEdges());
        h2.setAmountOfVertices(hyperEdges.get(hyperEdges.size()-1).getAmountOfVertices());
        return h2.connectHyperedges(metaGraph,hyperEdges);
    }

    public Hypergraph generateRandomHypergraph(int nodes, int tailSize, int maxCost, double p, int maxdepth, int seed){
        return new Hypergraph().generateRandomHypergraph(nodes,tailSize,maxCost,p, maxdepth, seed);
    }
}
