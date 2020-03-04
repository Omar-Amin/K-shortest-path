import java.util.ArrayList;

public class Edge {
    //Class attributes
    private int id;
    private int cost;
    private int kj; // number of nodes belonging to the tail of this edge
    private ArrayList<Vertex> tail = new ArrayList<>();
    private Vertex head = null;

    //Constructor
    //TODO: Add a cost in the near future (as argument to the object)
    public Edge(int identifier){
        this.id = identifier;
        this.cost = 1; // Skal nok ændres, magter bare ikke til vi skal skrive en random cost ind hver gang
    }

    //Functions for Edge
    public void addToTail(Vertex x){
        tail.add(x);
    }

    // Getter and setters for Edge
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public ArrayList<Vertex> getTail() {
        return tail;
    }

    public void setTail(ArrayList<Vertex> tail) {
        this.tail = tail;
    }

    public Vertex getHead() {
        return head;
    }

    public void setHead(Vertex head) {
        this.head = head;
    }

    public int getKj() { return kj; }

    public void setKj(int kj) { this.kj = kj; }
}
