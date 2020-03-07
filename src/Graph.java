import java.util.ArrayList;

public class Graph {
    int[] edgeLookup;
    int[] vertexLookup;
    int[] vertexTable;
    int[] edgeTable;
    public Graph(int[][] Matrix){
        int verticesCount = Matrix.length;
        int edgesCount = Matrix[0].length;
        vertexLookup = new int[verticesCount];
        edgeLookup = new int[edgesCount];
        int[][][] tempVertexTables = new int[verticesCount][][];
        int[][] tempEdgeTables = new int[edgesCount][];
        int[] edgeIndicatorCount = new int[edgesCount];
        for(int i = 0; i<edgesCount;i++) edgeIndicatorCount[i] = 0;
        int vertexInCount;
        int vertexOutCount;
        int count = 0;
        //Parse the graph for generation of the int arrays
        for(int vertex = 0; vertex < verticesCount;vertex++){
            vertexInCount = 0;
            vertexOutCount = 0;
            for(int edge = 0; edge < edgesCount;edge++){
                int indicator =  Matrix[vertex][edge];
                if(indicator == 1){
                    vertexInCount++;
                    edgeIndicatorCount[edge]++;
                    count++;
                }
                else if (indicator==-1){
                    vertexOutCount++;
                    edgeIndicatorCount[edge]++;
                    count++;
                }
            }
            //Initialize the correct size of the tempVertexTable
            tempVertexTables[vertex] = new int[2][];
            tempVertexTables[vertex][0] = new int[vertexInCount+1]; //First index indicates the next index to write in the table
            tempVertexTables[vertex][1] = new int[vertexOutCount+1];
            tempVertexTables[vertex][0][0] = 1;
            tempVertexTables[vertex][1][0] = 1;

            //Initialize the correct size of the tempEdgeTable
            for(int i = 0; i<edgesCount;i++){
                tempEdgeTables[i] = new int[edgeIndicatorCount[i]+1]; //First index indicates the next index to write in the table
                tempEdgeTables[i][0] = 2;
            }
        }
        int vertexIndex;
        int edgeIndex;
        for(int vertex = 0; vertex < verticesCount;vertex++){
            for(int edge = 0; edge < edgesCount;edge++) {
                int indicator =  Matrix[vertex][edge];
                if(indicator == 1){
                    vertexIndex = tempVertexTables[vertex][0][0];
                    tempVertexTables[vertex][0][vertexIndex] = edge;
                    tempEdgeTables[edge][1] = vertex; //Head is always on index 1
                    tempVertexTables[vertex][0][0]++;
                } else if (indicator==-1){
                    vertexIndex = tempVertexTables[vertex][1][0];
                    tempVertexTables[vertex][1][vertexIndex] = edge;
                    edgeIndex = tempEdgeTables[edge][0];
                    tempEdgeTables[edge][edgeIndex] = vertex;
                    tempEdgeTables[edge][0]++;
                    tempVertexTables[vertex][1][0]++;
                }
            }
        }
    }
}
