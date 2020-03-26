class Graph:
    edgeLookup = []
    vertexLookup = []
    edgeTable = []
    vertexTable = []

    def __init__(self, matrix, edgePrices):
        edgesCount = len(matrix[0])
        vertexCount = len(matrix)
        edgeLookup = [0] * edgesCount
        vertexLookup = [0] * vertexCount
        edgeIndicatorCount = [0] * edgesCount
        vertexIngoingCount = [0] * vertexCount
        if (edgesCount != len(edgePrices)):
            print("Defined input is not legal! There must be the same amount of edges as prices for each edge!")
            return
        count = 0
        for vertex in range(vertexCount):
            for edge in range(edgesCount):
                indicator = matrix[vertex][edge]
                if indicator == 1:
                    count += 1
                    vertexIngoingCount[vertex] += 1
                elif indicator == -1:
                    count += 1
                    edgeIndicatorCount[edge] += 1
        edgeTable = [0] * (count + edgesCount)
        vertexTable = [0] * (count + vertexCount * 2)
        for i in range(1, edgesCount):
            edgeLookup[i] = edgeLookup[i - 1] + edgeIndicatorCount[i - 1] + 2
        writeIndex = 0;
        for vertex in range(vertexCount):
            vertexLookup[vertex] = writeIndex;
            ingoingCount = vertexIngoingCount[vertex]
            vertexTable[writeIndex] = -1
            vertexTable[writeIndex + 1] = ingoingCount
            writeIndex += 2 + ingoingCount
            for edge in range(edgesCount):
                indicator = matrix[vertex][edge]
                if indicator != 0:
                    i = 0
                if indicator == 1:
                    edgeTable[edgeLookup[edge]] = vertex
                    edgeTable[edgeLookup[edge] + 1] = edgePrices[edge]
                    nextWrite = vertexLookup[vertex] + ingoingCount + 1
                    if nextWrite == 26:
                        i = 0
                    vertexTable[nextWrite] = edge
                    ingoingCount -= 1
                elif indicator == -1:
                    nextWrite = edgeIndicatorCount[edge] + edgeLookup[edge]
                    edgeIndicatorCount[edge] -= 1
                    if nextWrite == 26:
                        i = 0
                    edgeTable[nextWrite] = vertex
                    vertexTable[writeIndex] = edge
                    writeIndex+= 1
        self.vertexTable = vertexTable
        self.vertexLookup = vertexLookup
        self.edgeLookup = edgeLookup
        self.edgeTable = edgeTable

matrix = [[-1,-1, 0, 0, 0, 0, 0, 0, 0, 0],
          [ 1, 0,-1,-1, 0,-1, 0,-1, 0, 0],
          [ 0, 1,-1, 0, 0, 0, 0, 0, 0, 0],
          [ 0, 0, 1,-1,-1,-1, 0, 0, 0, 0],
          [ 0, 0, 0, 1, 0, 0,-1, 0, 0, 0],
          [ 0, 0, 0, 0, 1, 0, 0,-1, 0, 0],
          [ 0, 0, 0, 0, 0, 1, 0, 0,-1, 0],
          [ 0, 0, 0, 0, 0, 0, 1, 1, 1,-1],
          [ 0, 0, 0, 0, 0, 0, 0, 0, 0, 1]]
edgePrice = [1,2,3,4,5,6,7,8,9,10]

g = Graph(matrix,edgePrice)

