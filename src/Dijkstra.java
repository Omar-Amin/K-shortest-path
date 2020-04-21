import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/*
Reused code from the following github: https://gist.github.com/raymondchua/7954072
Modified a bit to translate it from and into hypergraphs easier.
 */

public class Dijkstra{

    public Dijkstra(){
    }

    public void computePaths(Node source){
        source.shortestDistance=0;

        //implement a priority queue
        PriorityQueue<Node> queue = new PriorityQueue<Node>();
        queue.add(source);

        while(!queue.isEmpty()){
            Node u = queue.poll();

			/*visit the adjacencies, starting from
			the nearest node(smallest shortestDistance)*/

            for(GEdge e: u.adjacencies){

                Node v = e.target;
                int weight = e.weight;

                //relax(u,v,weight)
                int distanceFromU = u.shortestDistance+weight;
                if(distanceFromU<v.shortestDistance){

					/*remove v from queue for updating
					the shortestDistance value*/
                    queue.remove(v);
                    v.shortestDistance = distanceFromU;
                    v.parent = u;
                    queue.add(v);

                }
            }
        }
    }

    public List<Node> getShortestPathTo(Node target){

        //trace path from target to source
        List<Node> path = new ArrayList<Node>();
        for(Node node = target; node!=null; node = node.parent){
            path.add(node);
        }


        //reverse the order such that it will be from source to target
        Collections.reverse(path);

        return path;
    }
}

//define Node
class Node implements Comparable<Node>{

    public final int value;
    public ArrayList<GEdge> adjacencies = new ArrayList<>();
    public int shortestDistance = Integer.MAX_VALUE;
    public Node parent;

    public Node(int val){
        this.value = val;
    }

    public int getValue() {
        return value;
    }

    public int compareTo(Node other){
        return Double.compare(shortestDistance, other.shortestDistance);
    }

}

//define Edge
class GEdge{
    public final Node target;
    public final int weight;
    public GEdge(Node targetNode, int weightVal){
        this.target = targetNode;
        this.weight = weightVal;
    }
}
