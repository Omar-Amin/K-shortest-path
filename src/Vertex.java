import java.util.ArrayList;

public class Vertex {

    // Class attributes
    private int id;
    private ArrayList<Edge> ingoing_edges;
    private ArrayList<Edge> outgoing_edges;

    //Class constructor
    public Vertex(int identifier) {
        this.id = identifier;
        //this.ingoing_edges = in;
        //this.outgoing_edges = out;
    }

    //Functions for Vertex
    public void addIngoingEdges(Edge x){
        ingoing_edges.add(x);
    }
    public void addOutgoingEdges(Edge x){
        outgoing_edges.add(x);
    }

    //Setters and Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Edge> getIngoing_edges() {
        return ingoing_edges;
    }

    public void setIngoing_edges(ArrayList<Edge> ingoing_edges) {
        this.ingoing_edges = ingoing_edges;
    }

    public ArrayList<Edge> getOutgoing_edges() {
        return outgoing_edges;
    }

    public void setOutgoing_edges(ArrayList<Edge> outgoing_edges) {
        this.outgoing_edges = outgoing_edges;
    }
}
