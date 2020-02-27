import java.util.ArrayList;

public class Vertex {

    // Class attributes
    private int id;
    private ArrayList<Edge> ingoing_edges;
    private ArrayList<Edge> outgoing_edges;

    //Class constructor
    public Vertex(int identifier, ArrayList<Edge> in, ArrayList<Edge> out) {
        this.id = identifier;
        this.ingoing_edges = in;
        this.outgoing_edges = out;
    }

    //Functions for Vertex



    //Setters and Getters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Edge> getIngoing_edges() {
        return ingoing_edges;
    } // Basically FS()

    public void setIngoing_edges(ArrayList<Edge> ingoing_edges) {
        this.ingoing_edges = ingoing_edges;
    }

    public ArrayList<Edge> getOutgoing_edges() {
        return outgoing_edges;
    }

    public void setOutgoing_edges(ArrayList<Edge> outgoing_edges) {
        this.outgoing_edges = outgoing_edges;
    } //Basically BS()
}
