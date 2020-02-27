import java.util.ArrayList;

public class Edge {
    //Class attributes
    private int id;
    private int cost;
    private ArrayList<Vertex> tail;
    private Vertex head;

    //Constructor
    public Edge(int identifier, ArrayList<Vertex> ingoing, Vertex outgoing){
        this.id = identifier;
        this.cost = 1; // Skal nok ændres, magter bare ikke til vi skal skrive en random cost ind hver gang
        this.tail = ingoing;
        this.head = outgoing;
    }

    //Functions for Edge


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
}
