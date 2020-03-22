import java.util.ArrayList;

public class Hypergraph {
    //Class attributes
    private int amountOfEdges;
    private int amountOfVertices;
    private ArrayList<Edge> Edges = new ArrayList<>();
    private ArrayList<Vertex> Vertices = new ArrayList<>();

    // constructor
    public Hypergraph(){
    }

    /**
     * Setting up the hypergraph by adding in-going and out-going edges
     * for each edge object in the array.
     *
     * @param matrix: The graph represented as a matrix.
     * */
    private Hypergraph setupHypergraph(int[][] matrix){
        int edgesChecked = 0;
        boolean[] currentlyChecked = new boolean[amountOfEdges];

        for (int row = 0; row < amountOfVertices; row++) {
            for (int col = 0; col < amountOfEdges; col++) {
                // skip checking rules since 0 means nothing goes in or out
                if(matrix[row][col] == -2){
                    continue;
                }
                if (matrix[row][col] < 0){ // -1 indicates outgoing edges
                    Vertices.get(row).addOutgoingEdges(Edges.get(col));
                    Edges.get(col).addToTail(Vertices.get(row));
                } else if (matrix[row][col] > 0) { // 1 indicates ingoing edges
                    Vertices.get(row).addIngoingEdges(Edges.get(col));
                    // if a head already exist, it means that the edge has more than
                    // two heads, which doesn't make it a b-graph
                    if (Edges.get(col).getHead() != null) {
                        throw new IllegalArgumentException("Wrong graph input, not a B-hypergraph");
                    }
                    Edges.get(col).setHead(Vertices.get(row));
                    if(Edges.get(col).getCost() == 0){
                        Edges.get(col).setCost(matrix[row][col]);

                    }
                }
                // checker for each the edges, it must contain at least one tail and
                // only one head, which is done by checking an boolean array and if
                // all edges hold the rule then it is a valid graph
                Edge edge = Edges.get(col);
                if(edge.getHead() != null && edge.getTail().size() > 0 && !currentlyChecked[col] ){
                    edgesChecked++;
                    currentlyChecked[col] = true;
                }
            }
        }

        if(edgesChecked != amountOfEdges){
            throw new IllegalArgumentException("Wrong graph input, all edges must contain at least one edge and one head");
        }
        return this;
    }

    /**
     * Create hypergraph by taking a matrix as input
     * @param matrix: input matrix
     * */
    public Hypergraph matrixInput(int[][] matrix){
        //Generate edges and vertices from matrix
        this.amountOfEdges = matrix[0].length;
        this.amountOfVertices = matrix.length;

        //Give edges and verticies an identifier number, and add to lists
        for (int id = 0; id < amountOfEdges; id++) {
            Edge edge = new Edge(id);
            Edges.add(edge);
        }
        for (int id = 0; id < amountOfVertices; id++) {
            Vertex vertex = new Vertex(id);
            Vertices.add(vertex);
        }

        return setupHypergraph(matrix);
    }

    /**
     * Create hypergraph by taking a list of edges as input. Primarily used
     * after finding the shortest path.
     * @param edges: Has to be as type ArrayList<Edge>
     * */
    //TODO: Return error if it is not a valid path (check rojin's definition)
    public Hypergraph edgesInput(ArrayList<Edge> edges){
        this.Edges = edges;

        for (int i = 0; i < edges.size(); i++) {
            Edge edge = Edges.get(i);
            for (Vertex v :edge.getTail()) {
                if(!Vertices.contains(v)){
                    Vertices.add(v);
                }
            }
            if(!Vertices.contains(edge.getHead())){
                Vertices.add(edge.getHead());
            }
        }

        for (Vertex vertex :Vertices) {
            vertex.getOutgoing_edges().removeIf(edge -> !edges.contains(edge));
            vertex.getIngoing_edges().removeIf(edge -> !edges.contains(edge));
        }

        return this;
    }

    /**
     * Method that prints out the hypergraph, mostly for debugging
     * */
    public void printHypergraph(){
        for (Edge edge:Edges) {
            System.out.println("Edge id: " + (edge.getId()+1));
            System.out.println("Edge out-going: " + (edge.getHead().getId()+1));
            String s = "";
            for (Vertex vertex :edge.getTail()) {
                s += (vertex.getId()+1) + ", ";
            }
            s = s.substring(0,s.length()-2);
            System.out.println("Edge in-going " + s);
            System.out.println("____________________");
        }
    }

    //Getter and setters
    public int getAmountOfEdges() {
        return amountOfEdges;
    }

    public void setAmountOfEdges(int amountOfEdges) {
        this.amountOfEdges = amountOfEdges;
    }

    public int getAmountOfVertices() {
        return amountOfVertices;
    }

    public void setAmountOfVertices(int amountOfVertices) {
        this.amountOfVertices = amountOfVertices;
    }

    public ArrayList<Edge> getEdges() {
        return Edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        Edges = edges;
    }

    public ArrayList<Vertex> getVertices() {
        return Vertices;
    }

    public void setVertices(ArrayList<Vertex> vertices) {
        Vertices = vertices;
    }
}
