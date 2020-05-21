import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.nio.ByteBuffer;

public class Graph {
    int[] edgeLookup;
    int[] vertexLookup;
    ByteBuffer vertexTable;
    int[] edgeTable;

    //Constructor for the graph class
    public Graph() {}
    /*
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
        vertexTable = new byte[Double.BYTES * count + Integer.BYTES*verticesCount*2]; //[IngoingCount,Ingoingedges, outgoing]
        //vertexTable = new int[count+verticesCount*2]; //[Cost, IngoingCount, Ingoingedges, outgoing]
        for (int i = 1; i < edgesCount; i++) {
            edgeLookup[i] = edgeLookup[i-1] + edgeIndicatorCount[i-1] + 2;
        }
        int ingoingCount,nextWrite;
        int outgoingWriteIndex = 0;
        for(int vertex = 0; vertex < verticesCount;vertex++) {
            vertexLookup[vertex] = outgoingWriteIndex;
            ingoingCount = vertexIngoingCount[vertex];
            vertexTable[outgoingWriteIndex] = -1; //Set cost to not initialized value (-1)
            Integer.toBinaryString(ingoingCount);
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
    }*/


    //Der skal nok kigges på om denne funktion kan optimeres
    protected void edgesInput(int[][] edges, int[] edgePrices){
        int edgesCount = edges.length;
        if(edgesCount != edgePrices.length) {
            System.out.println("Defined input is not legal! There must be the same amount of edges as prices for each edge!");
            return;
        }
        edgeLookup = new int[edgesCount];
        ArrayList<Integer> tempEdgeTable = new ArrayList<>();
        int max = -1;
        for (int i = 0; i < edgesCount; i++) {
            edgeLookup[i] = tempEdgeTable.size();
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
        //Er kun implementeret så det virker, er slet ikke optimeret:
        vertexLookup = new int[max+1];
        int count = 0;
        int[] vertexIngoingCount = new int[max+1];
        int[] vertexOutgoingCount= new int[max+1];
        for (int[] edge : edges) {
            vertexIngoingCount[edge[0]]++;
            count += edge.length;
            for (int j = 1; j < edge.length; j++) {
                vertexOutgoingCount[edge[j]]++;
            }
        }
        //Setup vertexLookup table
        vertexTable = ByteBuffer.allocateDirect((max+1)*(8+4)+count*4);
        //vertexTable = new byte[(max+1)*(8+4)+count*4];
        for (int i = 0; i < max; i++) {
            vertexLookup[i+1] = vertexLookup[i] + 8 + 4 + vertexIngoingCount[i]*4 + vertexOutgoingCount[i]*4;
            writeInt(vertexLookup[i]+8,vertexIngoingCount[i]);
        }
        //Write ingoing count for vertex
        writeInt(vertexLookup[max]+8,vertexIngoingCount[max]);
        //Fill in the vertexTable
        for (int i = 0; i < edgesCount; i++) {
            int[] edge = edges[i];
            int head = edge[0];
            //add ingoing edge to vertex
            writeInt(vertexLookup[head]+12+(--vertexIngoingCount[head])*4,i);
            for (int j = 1; j < edge.length; j++) {
                int v = edge[j];
                if(v >= max) {
                    //Add outgoing edge to vertex
                    writeInt(vertexTable.limit()-(vertexOutgoingCount[v]--)*4,i);
                }
                else {
                    //Add outgoing edge to vertex
                    writeInt(vertexLookup[v+1]-(vertexOutgoingCount[v]--)*4,i);
                }
            }
        }
        this.edgeTable = randomGenerator.convertListToArr(tempEdgeTable);
    }

    public void writeInt(int writeAt, int toWrite){
        vertexTable.putInt(writeAt,toWrite);
    }

    public void writeDouble(int writeAt,double toWrite){
        vertexTable.putDouble(writeAt,toWrite);
    }

    public int readInt(int readAt){
        return vertexTable.getInt(readAt);
    }

    public double readDouble(int readAt){
        return vertexTable.getDouble(readAt);
    }

    public int[] tail(int edge){
        int startIndex = edgeLookup[edge]+2; //First index is always 1 head
        int nextIndex;
        if(edge+1 < edgeLookup.length) nextIndex = edgeLookup[edge+1]; //Avoid out of bounds on edgeLookup
        else nextIndex = edgeTable.length;
        int size = nextIndex - startIndex;
        return new int[]{startIndex,size};
    }

    public int head(int edge){
        return edgeTable[edgeLookup[edge]];
    }

    public int[] FS(int vertex){
        int startIndex = vertexLookup[vertex];
        int nextIndex;
        if(vertex+1 < vertexLookup.length) nextIndex=vertexLookup[vertex+1];
        else nextIndex = vertexTable.limit();
        int size = nextIndex - startIndex;
        int ingoingEdges = readInt(startIndex+8);
        int outgoingEdges = (size - ingoingEdges*4 - 8 - 4)/4;
        startIndex += ingoingEdges*4+12;
        return new int[]{startIndex,outgoingEdges};
    }
    public int[] BS(int vertex){
        int startIndex = vertexLookup[vertex]+12;
        int ingoingEdges = readInt(startIndex-4);
        return new int[]{startIndex,ingoingEdges};
    }

    public Graph convertToNormalGraph(){
        Graph g = new Graph();
        ArrayList<int[]> edgeInput = new ArrayList<>();
        ArrayList<Integer> cost = new ArrayList<>();
        for (int i = 0; i < this.edgeLookup.length; i++) {
            int startIndex = this.edgeLookup[i];
            int nextIndex;
            if (i == this.edgeLookup.length-1) {
                nextIndex = this.edgeTable.length;
            } else {
                nextIndex = this.edgeLookup[i+1];
            }
            int head = edgeTable[startIndex];
            int edgeCost = edgeTable[startIndex+1];
            for (int j = startIndex+2; j < nextIndex; j++) {
                int[] edge = {head,edgeTable[j]};
                edgeInput.add(edge);
                cost.add(edgeCost);
            }
        }
        g.edgesInput(randomGenerator.convertListToArrArr(edgeInput),randomGenerator.convertListToArr(cost));
        return g;
    }

    public double getVertexCost(int vertex){
        return readDouble(vertexLookup[vertex]);
    }
    public int getEdgeCost(int edge){
        return edgeTable[edgeLookup[edge]+1];
    }

    public void setVertexCost(int vertex, double newcost){
        writeDouble(vertexLookup[vertex],newcost);
    }
}
