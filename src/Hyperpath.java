import java.util.ArrayList;

public class Hyperpath {
    private final ArrayList<Edge> path;
    private final double cost;
    private final ArrayList<Edge> deletedEdges;

    public Hyperpath(ArrayList<Edge> path, double cost, ArrayList<Edge> deletedEdges ){
        this.path = path;
        this.cost = cost;
        this.deletedEdges = deletedEdges;
    }

    public ArrayList<Edge> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }

    public ArrayList<Edge> getDeletedEdges() {
        return deletedEdges;
    }

    /**
     * Debugging purpose
     * */
    public void printPath(){
        System.out.println("Printing path...");
        for (Edge e :path) {
            System.out.println("ID: " + (e.getId()+1));
        }
    }
}
