import java.util.ArrayList;
import java.util.HashMap;

public class minPQ {
    private ArrayList<Vertex> heap;
    private HashMap<Integer, Integer> index;
    private int size;

    public minPQ(){
        heap = new ArrayList<>();
        index = new HashMap<>();
        this.size = 0;
    }

    public void minHeapify(int i){
        if (this.heap.size() == 0){
            return;
        }
        int smallest = i;
        int lChild = i*2+1;
        int rChild = i*2+2;
        if (lChild < this.size && heap.get(lChild).getCost() < heap.get(i).getCost()){
            smallest = lChild;
        }
        if (rChild < this.size && heap.get(rChild).getCost() < heap.get(smallest).getCost()){
            smallest = rChild;
        }
        if (smallest != i){
            exchange(i,smallest);
            minHeapify(smallest);
        }
    }

    public void decreaseValue(int id, int value){
        int i = index.get(id);
        heap.get(i).setCost(value);
        int parent = (int) Math.floor((i-1)/2);
        while (i > 0 && heap.get(parent).getCost() > heap.get(i).getCost()){
            exchange(parent,i);
            i = parent;
            parent = (int) Math.floor((i-1)/2);
        }
    }

    public void insert(Vertex obj){
        int i = this.size++;
        heap.add(obj);
        index.put(obj.getId(),i);
        int parentI = (int) Math.floor((i-1)/2);
        while(i > 0 && heap.get(parentI).getCost() > obj.getCost()) {
            exchange(i,parentI);
            i = parentI;
            parentI = (int) Math.floor((i - 1) / 2);
        }
    }

    public Vertex popMin(){
        if(size == 0) return null;
        Vertex ret = heap.get(0);
        if(size > 1)heap.set(0,heap.remove(size-1));
        else heap.remove(0);
        size--;
        minHeapify(0);
        return ret;
    }

    public boolean contains(Vertex obj){
        for (Vertex vertex: heap) {
            if(vertex.getId() == obj.getId()) return true;
        }
        return false;
    }

    private void exchange(int swap1, int swap2){
        Vertex swap1Obj = heap.get(swap1);
        Vertex swap2Obj = heap.get(swap2);
        heap.set(swap1, swap2Obj);
        index.put(swap1Obj.getId(),swap1);
        heap.set(swap2, swap1Obj);
        index.put(swap2Obj.getId(),swap2);
    }

    public void checkTree(){
        Vertex smallest = heap.get(0);
        for (Vertex v :heap) {
            if(smallest.getCost() > v.getCost()){
                System.out.println("error");
            }
        }
    }

    public int size(){
        return size;
    }
}