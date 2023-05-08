import java.util.Scanner;
import java.util.Stack;

public class Calculator {
    
    private static final String OPERATORS = "+-*/^";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the calculator app!");
        System.out.println("Please enter your equation:");

        String equation = scanner.nextLine();
        double result = evaluate(equation);

        System.out.println("Result: " + result);
    }

    private static double evaluate(String equation) {
        Stack<Double> operands = new Stack<>();
        Stack<Character> operators = new Stack<>();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                sb.append(c);
            } else if (OPERATORS.indexOf(c) != -1) {
                double operand = Double.parseDouble(sb.toString());
                sb.setLength(0);

                while (!operators.empty() && precedence(c, operators.peek())) {
                    double b = operands.pop();
                    double a = operands.pop();
                    operands.push(applyOperator(a, b, operators.pop()));
                }

                operators.push(c);
                operands.push(operand);
            } else if (c == '(') {
                operators.push(c);
            } else if (c == ')') {
                double operand = Double.parseDouble(sb.toString());
                sb.setLength(0);

                while (operators.peek() != '(') {
                    double b = operands.pop();
                    double a = operands.pop();
                    operands.push(applyOperator(a, b, operators.pop()));
                }

                operators.pop();
                operands.push(operand);
            }
        }

        if (sb.length() > 0) {
            double operand = Double.parseDouble(sb.toString());
            operands.push(operand);
        }

        while (!operators.empty()) {
            double b = operands.pop();
            double a = operands.pop();
            operands.push(applyOperator(a, b, operators.pop()));
        }

        return operands.pop();
    }

    private static boolean precedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        } else if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        } else if (op1 == '^' && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-')) {
            return false;
        } else {
            return true;
        }
    }

    private static double applyOperator(double a, double b, char op) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                return a / b;
            case '^':
                return Math.pow(a, b);
            default:
                throw new IllegalArgumentException("Invalid operator: " + op);
        }
    }
}
