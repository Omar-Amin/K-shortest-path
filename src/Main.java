import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[][] hypergraph = {
                {-1,-1, 0, 0, 0, 0, 0, 0, 0, 0},
                { 1, 0,-1,-1, 0,-1, 0,-1, 0, 0},
                { 0, 1,-1, 0, 0, 0, 0, 0, 0, 0},
                { 0, 0, 1,-1,-1,-1, 0, 0, 0, 0},
                { 0, 0, 0, 1, 0, 0,-1, 0, 0, 0},
                { 0, 0, 0, 0, 1, 0, 0,-1, 0, 0},
                { 0, 0, 0, 0, 0, 1, 0, 0,-1, 0},
                { 0, 0, 0, 0, 0, 0, 1, 1, 1,-1},
                { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}};
        Graph g = new Graph(hypergraph);
        for (int i = 0; i < g.edgeLookup.length; i++) {
            System.out.println("Edge "+i+":");
            System.out.println("Tail: " +Arrays.toString(g.tail(i)));
            System.out.println("Head: " + g.head(i)+"\n");
        }
        for (int i = 0; i < g.vertexLookup.length; i++) {
            System.out.println("Vertex " + i + ":");
            System.out.println("Forward Star: "+Arrays.toString(g.FS(i)));
            System.out.println("Back Star: "+Arrays.toString(g.BS(i))+"\n");
        }
    }
}
