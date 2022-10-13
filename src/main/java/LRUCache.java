import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class LRUCache {

    DLL que ;
    int cap;

    Map<Integer, DLLNode> map = new HashMap<>();

    public LRUCache(int capacity) {
        this.cap = capacity;
        que = new DLL(capacity);
    }

    public int get(int key) {
        if(!map.containsKey(key)){
            return -1;
        }
        DLLNode n = map.get(key);
        que.removeNode(n);
        que.addNode(n);
        return n.val;
    }

    public void put(int key, int value) {
        DLLNode n = null;
        if(map.containsKey(key)){
           n =  map.get(key);
           n.val = value;
           que.removeNode(n);
           que.addNode(n);
           return;
        }
        if(que.currSize >= cap){
            DLLNode old = que.tail.prev;
            que.removeNode(old);
            map.remove(old.key);
        }
        DLLNode newNode = new DLLNode(value);
        newNode.key = key;
        map.put(key,newNode);
        que.addNode(newNode);
    }


}
class DLL{

    int capacity;
    int currSize;
    LinkedList<DLLNode> que = new LinkedList<>();
    DLLNode head = new DLLNode(-1);
    DLLNode tail = new DLLNode(-1);

    DLL(int capacity){
        this.capacity = capacity;
        this.currSize =0;
        head.next = tail;
        tail.prev = head;
    }


    public void addNode(DLLNode  newNode){
        DLLNode next = head.next;
        head.next = newNode;
        newNode.next = next;
        next.prev = newNode;
        newNode.prev = head;
        currSize++;
    }

    public void removeNode(DLLNode node){
        DLLNode next = node.next;
        DLLNode prev = node.prev;
        prev.next = next;
        next.prev = prev;
        node.next = node.prev = null;
        currSize--;
    }

}
class DLLNode {
    int val;
    DLLNode next;
    DLLNode prev;
    int key;
    DLLNode(int val){
        this.val = val;
    }

}
