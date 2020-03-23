import java.util.ArrayList;

public class Graph {
    int[] edgeLookup;
    int[] vertexLookup;
    int[] vertexTable;
    int[] edgeTable;
    private WeightingFunctions price;

    //Constructor for the graph class
    public Graph(int[][] Matrix, int[] edgePrices){
        price = new WeightingFunctions(this);
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
                    nextWrite = (edgeIndicatorCount[edge]--) + edgeLookup[edge];
                    edgeTable[nextWrite] = vertex;
                    vertexTable[outgoingWriteIndex++] = edge;
                }
            }
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
        int[] ret = new int[outgoingEdges]; //Every edge got exactly one head.
        startIndex += ingoingEdges+2;
        int edge;
        for(int i = 0; i<outgoingEdges;i++){
            edge = vertexTable[startIndex+i];
            ret[i] = head(edge);
        }
        return ret;
    }
    public Object[] BS(int vertex){
        int startIndex = vertexLookup[vertex];
        int ingoingEdges = vertexTable[startIndex+1];
        ArrayList<Integer> ret = new ArrayList<>();
        int[] tail;
        int edge;
        for (int i = 1; i < ingoingEdges+1; i++) {
            edge = vertexTable[startIndex+i];
            tail = tail(edge);
            for (int V: tail) {
                if(!ret.contains(V)) ret.add(V);
            }
        }
        return ret.toArray();
    }
}
