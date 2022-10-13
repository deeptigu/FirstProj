import java.util.LinkedList;
import java.util.Stack;

public class BrowserHistory {

   /* public static void main(String[] args){

        int[][] grid = {{3,9},{7,12},{3,8},{6,8},{9,10},{2,9},{0,9},{3,9},{0,6},{2,8}};
        int[] arr = {2,4,5,0,1,3};
        BrowserHistory hl = new BrowserHistory();
        hl.("aabbcc",3);
        System.out.println();
    }*/
    Stack<String> stack ;
    int top;
    public BrowserHistory(String homepage) {
        stack = new Stack<>();
        stack.add(homepage);
        top = 0;
    }

    public void visit(String url) {
        stack.subList(top+1,stack.size()).clear();
        stack.add(++top,url);
    }

    public String back(int steps) {
        top = Math.min(0,top-steps);
        return stack.elementAt(top);
    }

    public String forward(int steps) {
        top = Math.max(top+steps,stack.size()-1);
        return stack.elementAt(top);
    }
}
