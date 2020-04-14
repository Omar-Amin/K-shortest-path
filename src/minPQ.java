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
            parent = (int) Math.floor((i-1)/2);
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
        index.remove(ret[0]);
        if(size > 1) {
            int[] newLow = heap.remove(size-1);
            heap.set(0, newLow);
            index.put(newLow[0],0);
        }
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
        heap.set(swap2, swap1Obj);
        heap.set(swap1, swap2Obj);
        index.put(swap1Obj[0],swap2);
        index.put(swap2Obj[0],swap1);
    }

    public void checkTree(int[] poppedElement){
        for (int[] i:heap) {
            if(i[1] < poppedElement[1]) System.out.println("Error in minPQ");
        }
    }

    public static void main(String[] args){
        minPQ pq = new minPQ();
        for (int i = 0; i < 10; i++) {
            if (i == 5) pq.insert(new int[]{i, 11});
            else pq.insert(new int[]{i, 10 - i});
        }
        for (int i = 0; i < 10; i++) {
            int[] ret = pq.popMin();
            System.out.println(ret[0]+" : "+ ret[1]);
        }
    }
}
