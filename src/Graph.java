import java.util.ArrayList;

public class Graph {
    int[] edgeLookup;
    int[] vertexLookup;
    int[] vertexTable;
    int[] edgeTable;

    //Constructor for the graph class
    public Graph() {
    }
    public void matrixInput(int[][] Matrix, int[] edgePrices){
        int verticesCount = Matrix.length;
        int edgesCount = Matrix[0].length;
        if(edgesCount != edgePrices.length) {
            System.out.println("Defined input is not legal! There must be the same amount of edges as prices for each edge!");
            return;
        }
        vertexLookup = new int[verticesCount];
        edgeLookup = new int[edgesCount];
        int[] edgeIndicatorCount = new int[edgesCount]; //Counter to count occurences of -1 and 1 in the matrix
        int[] vertexIngoingCount = new int[verticesCount]; //Counter to count number of ingoing edges to a vertex
        int count = 0;
        //Parse the graph matrix to count for generation of the int arrays
        for(int vertex = 0; vertex < verticesCount;vertex++){
            for(int edge = 0; edge < edgesCount;edge++){
                int indicator =  Matrix[vertex][edge];
                if(indicator == 1){
                    count++;
                    vertexIngoingCount[vertex]++;
                }
                else if (indicator==-1){
                    count++;
                    edgeIndicatorCount[edge]++;
                }
            }
        }
        edgeTable = new int[count+edgesCount]; //[Head, Cost, Tail,...]
        vertexTable = new int[count+(verticesCount*2)]; //[cost,IngoingCount,Ingoingedges, outgoing]
        for (int i = 1; i < edgesCount; i++) {
            edgeLookup[i] = edgeLookup[i-1] + edgeIndicatorCount[i-1] + 2;
        }
        int ingoingCount,nextWrite;
        int outgoingWriteIndex = 0;
        for(int vertex = 0; vertex < verticesCount;vertex++) {
            vertexLookup[vertex] = outgoingWriteIndex;
            ingoingCount = vertexIngoingCount[vertex];
            vertexTable[outgoingWriteIndex] = -1; //Set cost to not initialized value (-1)
            vertexTable[outgoingWriteIndex+1] = ingoingCount; //Set the number of ingoing edges to this vertex
            outgoingWriteIndex += ingoingCount+2;
            for (int edge = 0; edge < edgesCount; edge++) {
                int indicator = Matrix[vertex][edge];
                if (indicator == 1) {
                    edgeTable[edgeLookup[edge]] = vertex; //Only one head
                    edgeTable[edgeLookup[edge] + 1] = edgePrices[edge]; //Set price
                    nextWrite = vertexLookup[vertex] + ingoingCount-- +1;
                    vertexTable[nextWrite] = edge;
                } else if (indicator == -1) {
                    nextWrite = (edgeIndicatorCount[edge]--) + edgeLookup[edge] + 1;
                    edgeTable[nextWrite] = vertex;
                    vertexTable[outgoingWriteIndex++] = edge;
                }
            }
        }
    }

    protected void edgesInput(int[][] edges, int[] edgePrices){
        ArrayList<Integer> tempEdgeTable = new ArrayList<>();
        int max = -1;
        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int head = edge[0];
            tempEdgeTable.add(head);
            if(head>max) max = head;
            tempEdgeTable.add(edgePrices[i]);
            for (int j = 1; j< edge.length; j++) {
                int tail = edge[j];
                tempEdgeTable.add(tail);
                if(tail>max) max = tail;
            }
        }
        for (int edge = 0; edge < edges.length; edge++) {

        }
    }

    public int[] tail(int edge){
        int startIndex = edgeLookup[edge]+2; //First index is always 1 head
        int nextIndex;
        if(edge+1 < edgeLookup.length) nextIndex = edgeLookup[edge+1]; //Avoid out of bounds on edgeLookup
        else nextIndex = edgeTable.length;
        int size = nextIndex - startIndex;
        int[] ret = new int[size];
        for(int i = 0; i<size;i++){
            ret[i] = edgeTable[startIndex+i];
        }
        return ret;
    }

    public int head(int edge){
        return edgeTable[edgeLookup[edge]];
    }

    public int[] FS(int vertex){
        int startIndex = vertexLookup[vertex];
        int nextIndex;
        if(vertex+1 < vertexLookup.length) nextIndex=vertexLookup[vertex+1];
        else nextIndex = vertexTable.length;
        int size = nextIndex - startIndex;
        int ingoingEdges = vertexTable[startIndex+1];
        int outgoingEdges = size - ingoingEdges - 2;
        int[] ret = new int[outgoingEdges];
        startIndex += ingoingEdges+2;
        int edge;
        for(int i = 0; i<outgoingEdges;i++){
            edge = vertexTable[startIndex+i];
            ret[i] = edge;
        }
        return ret;
    }
    public int[] BS(int vertex){
        int startIndex = vertexLookup[vertex];
        int ingoingEdges = vertexTable[startIndex+1];
        int[] ret = new int[ingoingEdges];
        int edge;
        for (int i = 0; i < ingoingEdges; i++) {
            edge = vertexTable[startIndex+2+i];
            ret[i] = edge;
        }
        return ret;
    }

    public int getVertexCost(int vertex){
        return vertexTable[vertexLookup[vertex]];
    }
    public int getEdgeCost(int edge){
        return edgeTable[edgeLookup[edge]+1];
    }

    public void setVertexCost(int vertex, int newcost){
        vertexTable[vertexLookup[vertex]] = newcost;
    }
}
