public class Main {

    public static void main(String[] args) {
        int[][] hypergraph = {
                {-1,-1,0,0,0,0,0,0,0,0},
                {1,0,-1,-1,0,-1,0,-1,0,0},
                {0,1,-1,0,0,0,0,0,0,0},
                {0,0,1,-1,-1,-1,0,0,0,0},
                {0,0,0,1,0,0,-1,0,0,0},
                {0,0,0,0,1,0,0,-1,0,0},
                {0,0,0,0,0,1,0,0,-1,0},
                {0,0,0,0,0,0,1,1,1,-1},
                {0,0,0,0,0,0,0,0,0,1}};

        Hypergraph hg = new Hypergraph(hypergraph);
        hg.printHypergraph();
    }

}
