import java.util.ArrayList;

public class Vertex {

    // Class attributes
    private int id;
    private int cost;
    private Edge predecessor;
    private ArrayList<Edge> ingoing_edges = new ArrayList<>();
    private ArrayList<Edge> outgoing_edges = new ArrayList<>();

    //Class constructor
    //TODO: In the future add a cost to the object input
    public Vertex(int identifier) {
        this.id = identifier;
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Edge getPredecessor() { return predecessor; }

    public void setPredecessor(Edge predecessor) { this.predecessor = predecessor; }
}
