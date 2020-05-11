import java.util.ArrayList;

public class Vertex{

    // Class attributes
    private final int id;
    private double cost;
    private Edge predecessor;
    private final ArrayList<Edge> ingoing_edges = new ArrayList<>();
    private final ArrayList<Edge> outgoing_edges = new ArrayList<>();

    //Class constructor
    public Vertex(int identifier) {
        this.id = identifier;
        this.cost = 0;
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

    public ArrayList<Edge> getIngoing_edges() {
        return ingoing_edges;
    }

    public ArrayList<Edge> getOutgoing_edges() {
        return outgoing_edges;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public Edge getPredecessor() { return predecessor; }

    public void setPredecessor(Edge predecessor) { this.predecessor = predecessor; }
}
