import java.util.*;

public class Soln {

    public static void main(String[] args) {
        List<Point> p = new ArrayList<>();
        Point p1 = new Point(5,100);
        p.add(p1);
        Point p2 = new Point(15,120);
        p.add(p2);

    }

    List<Point> interpolate(List<Point> points,int interval){
        List<Point> res = new LinkedList<>();
        res.add(points.get(0));
        for(int i = 1 ; i < points.size();i++){
            if(points.get(i).x - res.get(i-1).x > interval){
                double m = (points.get(i).y - res.get(i-1).y)/(points.get(i).x - res.get(i-1).x);
                for( int x =  res.get(i-1).x+interval ; x < points.get(i).x ;x+=interval){
                    double y = m*x + res.get(i-1).y;
                    res.add(new Point(x,y));
                }
            }
            res.add(points.get(i));
        }
        return res;
    }


    Map<Node,Node> oldVsNew = new HashMap<>();
    public Node copyRandomList(Node head) {
        if(head == null){
            return null;
        }
        Node a = new Node(head.val);
        oldVsNew.put(head,a);
        a.next = copyRandomList(head.next);
        if(head.random != null){
         a.random = oldVsNew.get(head.random);
        }
        return a;
    }
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }
    static class Point{
        int x;
        double y;
        Point(int x, double y){
            this.x = x;
            this.y = y;
        }
    }
}
