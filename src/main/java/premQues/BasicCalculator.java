package premQues;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class BasicCalculator {

    public int calculate1(String s) {

        Stack<Integer> stack = new Stack<Integer>();
        int operand = 0;
        int result = 0; // For the on-going result
        int sign = 1;  // 1 means positive, -1 means negative

        for (int i = 0; i < s.length(); i++) {

            char ch = s.charAt(i);
            if (Character.isDigit(ch)) {

                // Forming operand, since it could be more than one digit
                operand = 10 * operand + (int) (ch - '0');

            } else if (ch == '+') {

                // Evaluate the expression to the left,
                // with result, sign, operand
                result += sign * operand;

                // Save the recently encountered '+' sign
                sign = 1;

                // Reset operand
                operand = 0;

            } else if (ch == '-') {

                result += sign * operand;
                sign = -1;
                operand = 0;

            } else if (ch == '(') {

                // Push the result and sign on to the stack, for later
                // We push the result first, then sign
                stack.push(result);
                stack.push(sign);

                // Reset operand and result, as if new evaluation begins for the new sub-expression
                sign = 1;
                result = 0;

            } else if (ch == ')') {

                // Evaluate the expression to the left
                // with result, sign and operand
                result += sign * operand;

                // ')' marks end of expression within a set of parenthesis
                // Its result is multiplied with sign on top of stack
                // as stack.pop() is the sign before the parenthesis
                result *= stack.pop();

                // Then add to the next operand on the top.
                // as stack.pop() is the result calculated before this parenthesis
                // (operand on stack) + (sign on stack * (result from parenthesis))
                result += stack.pop();

                // Reset the operand
                operand = 0;
            }
        }
        return result + (sign * operand);
    }

    public int calculate(String s) {
        Stack<Integer> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int n = s.length();

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                int val = s.charAt(i)-'0';
                while (i + 1 < n && Character.isDigit(s.charAt(i + 1))) {
                    //val = val * 10 + Character.getNumericValue(s.charAt(i + 1));
                    val = val * 10 + s.charAt(i+1)-'0';
                    i++;
                }
                operands.push(val);


            } else if (c == ' ') {
                continue;

            } else if (c == '(') {
                operators.push(c);

            } else if (c == ')') {
                while (operators.peek() != '(') {
                    operands.push(evaluate(operands, operators));
                }
                operators.pop();


            } else {  // while current operator' predence <= than stack top
                while (!operators.isEmpty() && comparePrecedence(c, operators.peek()) <= 0) {
                    operands.push(evaluate(operands, operators));
                }
                operators.push(c);
            }
        }

        while (!operators.isEmpty()) {
            operands.push(evaluate(operands, operators));
        }

        return operands.pop();
    }

    private int comparePrecedence(char a, char b) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('(', -1);
        map.put('+', 0);
        map.put('-', 0);
        map.put('*', 1);
        map.put('/', 1);
        return map.get(a) - map.get(b);
    }

    private int evaluate(Stack<Integer> operands, Stack<Character> operators) {
        int a = operands.pop();
        int b = operands.pop();
        char c = operators.pop();

        switch(c) {
            case '+' : return b+a;
            case '-': return b - a;
            case '*': return b * a;
            case '/': return b / a;
            default: return 0;
        }
    }
}
