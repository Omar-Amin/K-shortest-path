import java.util.ArrayList;

public class Hypergraph {
    //Class attributes
    private int amountOfEdges;
    private int amountOfVertices;
    private ArrayList<Edge> Edges;
    private ArrayList<Vertex> Vertices;

    //Constructor
    public Hypergraph(ArrayList[][] matrix){
        //Generate edges and vertices from matrix
    }

    //Functions for Hypergraph


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
