public class LinkedListLocal {

    public static void main(String[] args) {
        ListNode node = new ListNode(1);
        node.next = new ListNode(2);
        node.next.next = new ListNode(3);
        node.next.next.next = new ListNode(4);
        node.next.next.next.next = new ListNode(5);
        LinkedListLocal ll = new LinkedListLocal();
        System.out.println(ll.reverseKGroup(node,2));
    }

    public ListNode rotateRight(ListNode head, int k) {
        ListNode node = head, end = null;
        int size =0;
        while(node != null){
            size++;
            end = node;
            node = node.next;
        }
        if(size <= 1 || size == k){
            return head;
        }
        if(k > size){
            k = k % size;
        }
        k = size-k ;
        node = head;
        end.next = head;
        int i = 1;
        while(i <= k){
            end = node;
            node = node.next;
            i++;
        }
        end.next = null;
        head = node;
        return head;
    }

    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode newHead = null;
        ListNode curr = head, prevTail = null;
        while(curr != null){
            int i = 0;

            ListNode kThPtr = curr;
            while(kThPtr != null && i < k){
                kThPtr = kThPtr.next;
                i++;
            }
            if(i == k) {
                ListNode revHead = reverse(curr, k);
                if (newHead == null) {
                    newHead = revHead;
                }

                if(prevTail != null){
                    prevTail.next = revHead;
                }
                curr.next = kThPtr;
                prevTail = curr;
                curr = kThPtr;
            }
            else{
                break;
            }
        }
        return newHead;
    }
    ListNode tail = new ListNode();
    ListNode nextSubList = new ListNode();

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null)
            return head;
        int n = getCount(head);
        ListNode start = head;
        ListNode dummyHead = new ListNode();
        for (int size = 1; size < n; size = size * 2) {
            tail = dummyHead;
            while (start != null) {
                if (start.next == null) {
                    tail.next = start;
                    break;
                }
                ListNode mid = split(start, size);
                merge(start, mid);
                start = nextSubList;
            }
            start = dummyHead.next;
        }
        return dummyHead.next;
    }

    ListNode split(ListNode start, int size) {
        ListNode midPrev = start;
        ListNode end = start.next;
        //use fast and slow approach to find middle and end of second linked list
        for (int index = 1; index < size && (midPrev.next != null || end.next != null); index++) {
            if (end.next != null) {
                end = (end.next.next != null) ? end.next.next : end.next;
            }
            if (midPrev.next != null) {
                midPrev = midPrev.next;
            }
        }
        ListNode mid = midPrev.next;
        midPrev.next = null;
        nextSubList = end.next;
        end.next = null;
        // return the start of second linked list
        return mid;
    }

    void merge(ListNode list1, ListNode list2) {
        ListNode dummyHead = new ListNode();
        ListNode newTail = dummyHead;
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                newTail.next = list1;
                list1 = list1.next;
                newTail = newTail.next;
            } else {
                newTail.next = list2;
                list2 = list2.next;
                newTail = newTail.next;
            }
        }
        newTail.next = (list1 != null) ? list1 : list2;
        // traverse till the end of merged list to get the newTail
        while (newTail.next != null) {
            newTail = newTail.next;
        }
        // link the old tail with the head of merged list
        tail.next = dummyHead.next;
        // update the old tail to the new tail of merged list
        tail = newTail;
    }

    int getCount(ListNode head) {
        int cnt = 0;
        ListNode ptr = head;
        while (ptr != null) {
            ptr = ptr.next;
            cnt++;
        }
        return cnt;
    }


    private ListNode reverse(ListNode root, int k) {
        ListNode curr = root, prev = null;
        while(k>0){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            k--;
        }
        return prev;
    }


    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }
}
