import java.util.ArrayList;
import java.util.HashMap;

public class minPQ {
    private final ArrayList<Vertex> heap;
    private final HashMap<Integer, Integer> index;
    private int size;

    public minPQ(){
        heap = new ArrayList<>();
        index = new HashMap<>();
        this.size = 0;
    }

    public void minHeapify(int i){
        int smallest = i;
        int lChild = i*2+1;
        int rChild = i*2+2;
        if (lChild < size && heap.get(lChild).getCost() < heap.get(i).getCost()){
            smallest = lChild;
        }
        if (rChild < size && heap.get(rChild).getCost() < heap.get(smallest).getCost()){
            smallest = rChild;
        }
        if (smallest != i){
            exchange(smallest,i);
            minHeapify(smallest);
        }
    }

    public void decreaseValue(int id, double value){
        int i = index.get(id);
        heap.get(i).setCost(value);
        int parent = (i-1)/2;
        while (i > 0 && heap.get(parent).getCost() > heap.get(i).getCost()){
            exchange(parent,i);
            i = parent;
            parent = (i-1)/2;
        }
    }

    public void insert(Vertex obj){
        int i = size++;
        heap.add(obj);
        index.put(obj.getId(),i);
        int parent = (i-1)/2;
        while(i > 0 && heap.get(parent).getCost() > obj.getCost()) {
            exchange(parent,i);
            i = parent;
            parent = (i-1)/2;
        }
    }

    public Vertex popMin(){
        if(size == 0){
            return null;
        }
        Vertex ret = heap.get(0);
        index.remove(ret.getId());
        if(size > 1){
            heap.set(0,heap.remove(size-1));
            index.put(heap.get(0).getId(),0);
        } else {
            heap.remove(0);
        }

        size--;
        minHeapify(0);
        return ret;
    }

    public boolean contains(Vertex obj){
        return !(index.get(obj.getId()) == null);
    }

    private void exchange(int swap1, int swap2){
        Vertex swap1Obj = heap.get(swap1);
        Vertex swap2Obj = heap.get(swap2);
        heap.set(swap1, swap2Obj);
        heap.set(swap2, swap1Obj);
        index.put(swap1Obj.getId(),swap2);
        index.put(swap2Obj.getId(),swap1);
    }

    public int size(){
        return size;
    }

}