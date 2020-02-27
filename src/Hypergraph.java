import java.util.ArrayList;

public class Hypergraph {
    //Class attributes
    private int amountOfEdges;
    private int amountOfVertices;
    private ArrayList<Edge> Edges;
    private ArrayList<Vertex> Vertices;

    //Constructor
    public Hypergraph(int[][] matrix, int numEdges, int numVertices){
        //Generate edges and vertices from matrix
        this.amountOfEdges = numEdges;
        this.amountOfVertices = numVertices;

        //Give edges and verticies an identifier number, and add to lists
        for (int id = 0; id < numEdges-1; id++) {
            Edge temp = new Edge(id);
            Edges.add(temp);
        }
        for (int id = 0; id <= numVertices-1; id++) {
            Vertex temp = new Vertex(id);
            Vertices.add(temp);
        }
        //Set the vertices in and outgoing edges, and edges tail and head
        setupHypergraph(matrix);
    }

    //Functions for Hypergraph
    public void setupHypergraph(int[][] matrix){
        int tempEdgeNum = amountOfEdges;
        int tempVertexNum = amountOfVertices;
        // Vertices on rows, edges on columns
        for (int row = 0; row <= tempVertexNum-1; row++) {
            for (int col = 0; col <= tempEdgeNum-1; col++) {
                if (matrix[row][col] == -1){ // -1 indicates outgoing edges
                    Vertices.get(row).addOutgoingEdges(Edges.get(col));
                    Edges.get(col).addToTail(Vertices.get(row));
                }
                else if (matrix[row][col] == 1){ // 1 indicates ingoing edges
                    Vertices.get(row).addIngoingEdges(Edges.get(col));
                    if (Edges.get(col).getHead() != null) {
                        //Så har der været lavet et head før, og der må have været sket en fejl
                        // Eller en fejl i givene graf, kast error
                    }
                    Edges.get(col).setHead(Vertices.get(row));
                }
                else{ // 0 indicates no edges
                    continue;
                }
            }
        }
    }

    //Getter and setters
    public int getAmountOfEdges() {
        return amountOfEdges;
    }

    public void setAmountOfEdges(int amountOfEdges) {
        this.amountOfEdges = amountOfEdges;
    }

    public int getAmountOfVertices() {
        return amountOfVertices;
    }

    public void setAmountOfVertices(int amountOfVertices) {
        this.amountOfVertices = amountOfVertices;
    }

    public ArrayList<Edge> getEdges() {
        return Edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        Edges = edges;
    }

    public ArrayList<Vertex> getVertices() {
        return Vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        Vertices = vertices;
    }
}
