import java.util.*;

public class Hypergraph {
    //Class attributes
    private int amountOfEdges = 0;
    private int amountOfVertices = 0;
    private ArrayList<Edge> Edges = new ArrayList<>();
    private ArrayList<Vertex> Vertices = new ArrayList<>();
    private Vertex source;
    private Vertex target;

    // constructor
    public Hypergraph(){
    }


    /**
     * Create hypergraph by taking a matrix as input
     * @param matrix: input matrix
     * */
    public Hypergraph matrixInput(int[][] matrix){
        //Generate edges and vertices from matrix
        int edges = matrix[0].length;
        int vertices = matrix.length;

        //Give edges and verticies an identifier number, and add to lists
        makeEdges(edges);
        makeVertices(vertices);

        return setupHypergraph(matrix);
    }

    public Hypergraph arraylistInput(ArrayList<Integer[]> edges, ArrayList<Integer> cost){
        int maxVertex = 0;
        for (Integer[] edge :edges) {
            for (Integer i :edge) {
                if(maxVertex < i){
                    maxVertex = i;
                }
            }
        }
        makeVertices(maxVertex+1);

        for (int i = 0; i < edges.size(); i++) {
            Integer[] edge = edges.get(i);
            Edge edge1 = new Edge(amountOfEdges++);
            edge1.setCost(cost.get(i));
            edge1.setHead(Vertices.get(edge[0]));
            for (Integer v :edge) {
                edge1.addToTail(Vertices.get(v));
            }
        }

        return this;
    }

    /**
     * Setting up the hypergraph by adding in-going and out-going edges
     * for each edge object in the array.
     *
     * @param matrix: The graph represented as a matrix.
     * */
    private Hypergraph setupHypergraph(int[][] matrix){
        int edges = matrix[0].length;
        int vertices = matrix.length;

        int edgesChecked = 0;
        boolean[] currentlyChecked = new boolean[edges];

        for (int row = 0; row < vertices; row++) {
            for (int col = 0; col < edges; col++) {
                // skip checking rules since 0 means nothing goes in or out
                if(matrix[row][col] == -2){
                    continue;
                }
                if (matrix[row][col] < 0){ // -1 indicates outgoing edges
                    Vertices.get(row).addOutgoingEdges(Edges.get(col));
                    Edges.get(col).addToTail(Vertices.get(row));
                } else if (matrix[row][col] >= 0) { // 1 indicates ingoing edges
                    Vertices.get(row).addIngoingEdges(Edges.get(col));
                    // if a head already exist, it means that the edge has more than
                    // two heads, which doesn't make it a b-graph
                    if (Edges.get(col).getHead() != null) {
                        throw new IllegalArgumentException("Wrong graph input, not a B-hypergraph");
                    }
                    Edges.get(col).setHead(Vertices.get(row));
                    Edges.get(col).setCost(matrix[row][col]);
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

        if(edgesChecked != edges){
            throw new IllegalArgumentException("Wrong graph input, all edges must contain at least one edge and one head");
        }
        return this;
    }

    /**
     * Create hypergraph by taking a list of edges as input. Primarily used
     * after finding the shortest path.
     * @param edges: Has to be as type ArrayList<Edge>
     * */
    //TODO: Return error if it is not a valid path (check rojin's definition)
    public Hypergraph edgesInput(ArrayList<Edge> edges){

        for (Edge edge :edges) {
            ArrayList<Vertex> newTail = new ArrayList<>();
            Edge newEdge = new Edge(edge.getId());
            newEdge.setCost(edge.getCost());
            for (Vertex v :edge.getTail()) {
                Vertex newVertex = findVertex(v);
                newVertex.addOutgoingEdges(newEdge);
                newTail.add(newVertex);
            }
            Vertex head = edge.getHead();
            Vertex newVertex = findVertex(head);
            newVertex.addIngoingEdges(newEdge);
            newEdge.setHead(newVertex);
            newEdge.setTail(newTail);
            Edges.add(newEdge);
        }

        return this;
    }

    /**
     * Searches if the vertex exist, if it doesn't a new vertex is created.
     * @param v: Vertex so search if it exist.
     * */
    private Vertex findVertex(Vertex v){
        for (Vertex vertex :Vertices) {
            if(v.getId() == vertex.getId()){
                return vertex;
            }
        }
        Vertex vertex = new Vertex(v.getId());
        Vertices.add(vertex);
        return vertex;
    }

    /**
     * Method that prints out the hypergraph, mostly for debugging
     * */
    public void printHypergraph(){
        for (Edge edge:Edges) {
            System.out.println("Edge id: " + (edge.getId()+1));
            System.out.println("Edge out-going: " + (edge.getHead().getId()+1));
            StringBuilder s = new StringBuilder();
            for (Vertex vertex :edge.getTail()) {
                s.append(vertex.getId() + 1).append(", ");
            }
            s = new StringBuilder(s.substring(0, s.length() - 2));
            System.out.println("Edge in-going " + s);
            System.out.println("____________________");
        }
    }

    /**
     * Generates random graph by some given input.
     * */
    public Hypergraph generateRandomHyperpath(int maxNodes, int minNodes , int tailSize, int maxCost, int seed){
        Random rand;
        if(seed >= 0){
            rand = new Random(seed);
        }else {
            rand = new Random();
        }

        int amountOfNodes = rand.nextInt((maxNodes - minNodes) + 1) + minNodes;
        if(tailSize >= amountOfNodes){
            tailSize = amountOfNodes-1;
        }
        int randTailsize = rand.nextInt((tailSize - 1) + 1) + 1;

        int[][] matrix = new int[amountOfNodes][amountOfNodes-1];

        for (int[] ints : matrix) {
            Arrays.fill(ints, -2);
        }

        // first we fill it the matrix diagonally with random values (as outgoing edges)
        for (int row = 1; row < amountOfNodes; row++) {
            int randCost = rand.nextInt((maxCost - 1) + 1) + 1;
            matrix[row][row-1] = randCost;
        }

        // depending on the tail size, we fill it with -1 diagonally so
        // each edge has the amount of -1 as the tail size
        for (int j = 0; j < randTailsize; j++) {
            for (int i = 0; i < amountOfNodes-(1+j); i++) {
                matrix[i][i+j] = -1;
            }
        }

        return matrixInput(matrix);
    }

    /**
     * Connect hyperedges into a hypergraph from a list of hyperpaths and a metagraph.
     * */
    public Hypergraph connectHyperedges(int[][] metaGraph,ArrayList<Hypergraph> hyperedges){
        int edges = metaGraph[0].length;
        int vertices = metaGraph.length;

        // we connect an edge from vertex to the start of the hyperedge
        // and an edge to the end of the hyperedge, thus we need
        // twice as many edges
        makeEdges(edges*2);
        makeVertices(vertices);

        this.source = Vertices.get(0);
        this.target = Vertices.get(vertices-1);

        // adding all vertices and edges from the hyperedges to the list
        for (Hypergraph h :hyperedges) {
            Vertices.addAll(h.getVertices());
            Edges.addAll(h.getEdges());
        }

        for (int row = 0; row < vertices; row++) {
            for (int col = 0; col < edges; col++) {
                // connect for outgoing edges
                if (metaGraph[row][col] == -1){
                    Vertices.get(row).addOutgoingEdges(Edges.get(col));
                    Edges.get(col).addToTail(Vertices.get(row));
                    Edges.get(col).setHead(hyperedges.get(col).getVertices().get(0));
                    hyperedges.get(col).getVertices().get(0).getIngoing_edges().add(Edges.get(col));
                }
                // connect for ingoing edges
                if(metaGraph[row][col] == 1){
                    Vertices.get(row).addIngoingEdges(Edges.get(edges+col));
                    // edges+col is the position for the edge on the end of hyperedge
                    Edges.get(edges+col).setHead(Vertices.get(row));
                    int size = hyperedges.get(col).getVertices().size();
                    Edges.get(edges+col).addToTail(hyperedges.get(col).getVertices().get(size-1));
                    hyperedges.get(col).getVertices().get(size-1).getOutgoing_edges().add(Edges.get(col+edges));
                }
            }
        }

        return this;
    }

    /**
     * Making random hypergraph inspired by a wikipedia article:
     * https://en.wikipedia.org/wiki/Erd%C5%91s%E2%80%93R%C3%A9nyi_model
     * */
    public Hypergraph generateRandomHypergraph(int nodes, int tailSize, int maxCost, double p,int maxdepth, int seed){
        Random rand = new Random();
        if(seed > 0){
            rand = new Random(seed);
        }

        makeVertices(nodes);
        for (int i = amountOfVertices-1; i > 0; i--) {
            int counter = 0;
            for (int j = i-1; j >= 0; j--) {
                double q = rand.nextDouble();
                if (counter == maxdepth){
                    continue;
                }
                if(q <= p){
                    counter++;
                    Edge edge = new Edge(amountOfEdges++);
                    int randCost = rand.nextInt((maxCost - 1) + 1) + 1;
                    edge.setCost(randCost);
                    edge.addToTail(Vertices.get(j));
                    edge.setHead(Vertices.get(i));
                    Vertices.get(j).addOutgoingEdges(edge);
                    Vertices.get(i).addIngoingEdges(edge);

                    double newp = tailSize/((double)j);

                    for (int k = j-1; k >= 0; k--) {
                        double newq = rand.nextDouble();
                        if (newq <= newp){
                            edge.addToTail(Vertices.get(k));
                            Vertices.get(k).addOutgoingEdges(edge);
                        }
                    }
                    Edges.add(edge);
                }
            }
        }

        this.source = Vertices.get(0);
        this.target = Vertices.get(amountOfVertices-1);

        return this;
    }

    private void makeVertices(int amount){
        for (int id = 0; id < amount; id++) {
            Vertex vertex = new Vertex(amountOfVertices++);
            Vertices.add(vertex);
        }
    }

    private void makeEdges(int amount){
        for (int id = 0; id < amount; id++) {
            Edge edge = new Edge(amountOfEdges++);
            Edges.add(edge);
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

    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }
}
