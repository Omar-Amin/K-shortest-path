import java.util.ArrayList;

public class Graph {
    int[] edgeLookup;
    int[] vertexLookup;
    int[] vertexTable;
    int[] edgeTable;

    //Constructor for the graph class
    public Graph(int[][] Matrix){
        int verticesCount = Matrix.length;
        int edgesCount = Matrix[0].length;
        vertexLookup = new int[verticesCount];
        edgeLookup = new int[edgesCount];
        int[] edgeIndicatorCount = new int[edgesCount];
        int[] vertexIngoingCount = new int[verticesCount];
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
        edgeTable = new int[count]; //[Head, Tail...]
        vertexTable = new int[count+verticesCount]; //[IngoingCount]
        for (int i = 1; i < edgesCount; i++) {
            edgeLookup[i] = edgeLookup[i-1] + edgeIndicatorCount[i-1] + 1;
        }
        int ingoingCount,nextWrite;
        int outgoingIndex = 0;
        for(int vertex = 0; vertex < verticesCount;vertex++) {
            vertexLookup[vertex] = outgoingIndex;
            ingoingCount = vertexIngoingCount[vertex];
            vertexTable[outgoingIndex] = ingoingCount;
            outgoingIndex += ingoingCount+1;
            for (int edge = 0; edge < edgesCount; edge++) {
                int indicator = Matrix[vertex][edge];
                if(indicator == 1){
                    edgeTable[edgeLookup[edge]] = vertex; //Only one head
                    nextWrite = vertexLookup[vertex] + ingoingCount--;
                    vertexTable[nextWrite] = edge;
                } else if(indicator == -1){
                      nextWrite = (edgeIndicatorCount[edge]--) + edgeLookup[edge];
                    edgeTable[nextWrite] = vertex;
                    vertexTable[outgoingIndex++] = edge;
                }
            }
        }
    }

    public int[] tail(int edge){
        int startIndex = edgeLookup[edge]+1; //First index is always 1 head
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
        int ingoingEdges = vertexTable[startIndex];
        int outgoingEdges = size - ingoingEdges - 1;
        int[] ret = new int[outgoingEdges]; //Every edge got exactly one head.
        startIndex += ingoingEdges+1;
        int edge;
        for(int i = 0; i<outgoingEdges;i++){
            edge = vertexTable[startIndex+i];
            ret[i] = head(edge);
        }
        return ret;
    }
    public Object[] BS(int vertex){
        int startIndex = vertexLookup[vertex];
        int ingoingEdges = vertexTable[startIndex];
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
