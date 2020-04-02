import java.util.ArrayList;
import java.util.HashMap;

public class minPQ {
    ArrayList<int[]> heap;
    HashMap<Integer, Integer> index;
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
        if (lChild <this.size && heap.get(lChild)[1] < heap.get(i)[1]) smallest = lChild;
        if (rChild <this.size && heap.get(rChild)[1] < heap.get(smallest)[1]) smallest = rChild;
        if (smallest != i){
            exchange(i,smallest);
            minHeapify(smallest);
        }
    }

    public boolean decreaseValue(int id, int value){
        int i = index.get(id);
        heap.get(i)[1] = value;
        int parent = (int) Math.floor((i-1)/2);
        while (i > 0 && heap.get(parent)[1] > heap.get(i)[1]){
            exchange(parent,i);
            i = parent;
        }
        return true;
    }

    public boolean insert(int[] obj){
        int i = this.size++;
        heap.add(obj);
        index.put(obj[0],i);
        int parentI = (int) Math.floor((i-1)/2);
        while(i > 0 && heap.get(parentI)[1] > heap.get(i)[1]) {
            exchange(i,parentI);
            i = parentI;
            parentI = (int) Math.floor((i - 1) / 2);
        }
        return true;
    }

    public int[] getMin(){
        return heap.get(0);
    }

    public int[] popMin(){
        if(size == 0) return null;
        int[] ret = heap.get(0);
        if(size > 1)heap.set(0,heap.remove(size-1));
        else heap.remove(0);
        size--;
        minHeapify(0);
        return ret;
    }

    public boolean contains(int obj){
        for (int[] vertex: heap) {
            if(vertex[0] == obj) return true;
        }
        return false;
    }

    public void exchange(int swap1, int swap2){
        int[] swap1Obj = heap.get(swap1);
        int[] swap2Obj = heap.get(swap2);
        heap.set(swap1, swap2Obj);
        index.put(swap1Obj[0],swap1);
        heap.set(swap2, swap1Obj);
        index.put(swap2Obj[0],swap2);
    }

    public void checkTree(){
        for (int i = 0; i < size; i++) {
            int lChild = i * 2 + 1;
            int rChild = i * 2 + 2;
            System.out.println(heap.get(i)[1]);
            if (rChild < size) System.out.println("lChild:" + heap.get(lChild)[1] + " rChild:" + heap.get(rChild)[1] + "\n");
            else if (lChild < size) System.out.println("lChild:" + heap.get(lChild)[1] + "\n");
        }
    }
}
