import java.util.ArrayList;

public class minPQ {
    ArrayList<int[]> heap;
    int size;

    public minPQ(){
        heap = new ArrayList<>();
        this.size = 0;
    }

    public void minHeapify(int i){
        if (size == 0) return;
        int smallest = i;
        int lChild = i*2+1;
        int rChild = i*2+2;
        if (lChild <this.size && heap.get(lChild)[1] < heap.get(i)[1]) smallest = lChild;
        if (rChild <this.size && heap.get(rChild)[1] < heap.get(smallest)[1]) smallest = rChild;
        if (smallest != i){
            int[] tempObj = heap.get(i);
            heap.set(i,heap.get(smallest));
            heap.set(smallest,tempObj);
            minHeapify(smallest);
        }
    }

    public boolean insert(int[] obj){
        int[] tempObj;
        int i = this.size++;
        heap.add(obj);
        int parentI = (int) Math.floor((i-1)/2);
        while(i > 0 && heap.get(parentI)[1] > heap.get(i)[1]) {
            tempObj = heap.get(parentI);
            heap.set(parentI, heap.get(i));
            heap.set(i, tempObj);
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
