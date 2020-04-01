import java.util.ArrayList;

public class Graph {

    private ArrayList<Node> nodes = new ArrayList<>();

    public Graph(int lengthOfMeta){
        Node firstNode = new Node(1);
        nodes.add(firstNode);
        for (int i = 2; i <= lengthOfMeta; i++) {
            Node node = new Node(i);
        }
    }

}
