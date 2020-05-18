import java.util.ArrayList;
import java.util.HashMap;

public class minPQ {
    ArrayList<Object[]> heap;
    HashMap<Object, Integer> index;
    int size;

    public minPQ(){
        heap = new ArrayList<>();
        index = new HashMap<>();
        this.size = 0;
    }

    public void minHeapify(int i){
        if (this.heap.size() == 0) return;
        int smallest = i;
        int lChild = i*2+1;
        int rChild = i*2+2;
        if (lChild <this.size && (double) heap.get(lChild)[1] < (double) heap.get(i)[1]) smallest = lChild;
        if (rChild <this.size && (double) heap.get(rChild)[1] < (double) heap.get(smallest)[1]) smallest = rChild;
        if (smallest != i){
            exchange(i,smallest);
            minHeapify(smallest);
        }
    }

    public void decreaseValue(Object id, double value){
        int i = index.get(id);
        heap.get(i)[1] = value;
        int parent = (i-1)/2;
        while (i > 0 && (double) heap.get(parent)[1] > (double) heap.get(i)[1]){
            exchange(parent,i);
            i = parent;
            parent = (i-1)/2;
        }
    }

    public void insert(Object[] obj){
        int i = this.size++;
        heap.add(obj);
        index.put(obj[0],i);
        int parent = (i-1)/2;
        while(i > 0 && (double) heap.get(parent)[1] > (double) heap.get(i)[1]) {
            exchange(i,parent);
            i = parent;
            parent = (i-1)/2;
        }
    }

    public Object[] popMin(){
        if(size == 0) return null;
        Object[] ret = heap.get(0);
        index.remove(ret[0]);
        if(size > 1) {
            heap.set(0, heap.remove(size-1));
            index.put(heap.get(0)[0],0);
        }
        else heap.remove(0);
        size--;
        minHeapify(0);
        return ret;
    }

    public boolean contains(int obj){
        return index.get(obj) != null;
    }

    public void exchange(int swap1, int swap2){
        Object[] swap1Obj = heap.get(swap1);
        Object[] swap2Obj = heap.get(swap2);
        heap.set(swap2, swap1Obj);
        heap.set(swap1, swap2Obj);
        index.put(swap1Obj[0],swap2);
        index.put(swap2Obj[0],swap1);
    }

}
