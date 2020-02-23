public interface Vertices {
    public int[] FS(); //returns all the vertices that the input got an edge to
    public int[] BS(); //list of vertices pointing to input vertex
    public int[] listOfVertices(); //Bruges til debugging
    public int[] predecessor();
}
