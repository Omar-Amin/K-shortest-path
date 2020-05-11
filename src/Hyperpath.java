import java.util.ArrayList;
import java.util.HashMap;

public class Hyperpath {
    private final ArrayList<Edge> path;
    private final double cost;
    private final HashMap<Integer,Integer> deletedEdges;
    private int version;

    public Hyperpath(ArrayList<Edge> path, double cost, HashMap<Integer,Integer> deletedEdges ){
        this.path = path;
        this.cost = cost;
        this.deletedEdges = deletedEdges;
        this.version = path.size()-1;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
