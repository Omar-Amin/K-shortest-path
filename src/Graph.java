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
                    vertexIndex = tempVertexTables[vertex][0][0]++;
                    tempVertexTables[vertex][0][vertexIndex] = edge;
                    tempEdgeTables[edge][1] = vertex; //Head is always on index 1
                } else if (indicator==-1){
                    vertexIndex = tempVertexTables[vertex][1][0]++;
                    tempVertexTables[vertex][1][vertexIndex] = edge;
                    edgeIndex = tempEdgeTables[edge][0]++;
                    tempEdgeTables[edge][edgeIndex] = vertex;
                }
            }
        }
        //Flatten the tempVertexTable
        int writeIndex = 0;
        vertexTable = new int[count+verticesCount];
        for (int vertex = 0; vertex < verticesCount;vertex++) {
            vertexLookup[vertex] = writeIndex;
                vertexTable[writeIndex++] = tempVertexTables[vertex][0].length-1;
            for(int i = 1; i < tempVertexTables[vertex][0].length;i++){
                vertexTable[writeIndex++] = tempVertexTables[vertex][0][i];
            }
            for(int i = 1; i < tempVertexTables[vertex][1].length;i++){
                vertexTable[writeIndex++] = tempVertexTables[vertex][1][i];
            }
        }
        //Flatten the tempEdgeTable
        writeIndex = 0;
        edgeTable = new int[count];
        for (int edge = 0; edge < edgesCount; edge++) {
            edgeLookup[edge] = writeIndex;
            for (int i = 1; i<tempEdgeTables[edge].length;i++){
                edgeTable[writeIndex++] = tempEdgeTables[edge][i];
            }
        }
    }

    public int[] tail(int edge){
        int startIndex = edgeLookup[edge]+1; //First index is always 1 head
        int nextIndex;
        if(edge+1 < edgeLookup.length) nextIndex = edgeLookup[edge+1];
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

}
