import java.util.ArrayList;
import java.util.HashMap;

public class Hyperpath {
    private final ArrayList<Edge> path;
    private final double cost;
    private final HashMap<Integer,Integer> deletedEdges;

    public Hyperpath(ArrayList<Edge> path, double cost, HashMap<Integer,Integer>deletedEdges ){
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

    public HashMap<Integer,Integer> getDeletedEdges() {
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
