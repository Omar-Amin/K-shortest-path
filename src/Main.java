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
        int[] edgePrice = {1,2,3,4,5,6,7,8,9,10};
        Graph g = new Graph(hypergraph,edgePrice);
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
