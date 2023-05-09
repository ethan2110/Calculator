import java.util.*; //Imports the java Stack and Scanner classes needed for the method
// Stack enables all data to operations at one end only. So the only element that can be removed is the element at the top of the stack, and only one item can be read or removed at a given time
//also using stack so I can do pemdas operations more easily, stack allows me to manipulate the order of the array more easily than having to use an array list as thinga re added from the top and removed from the bottom

public class Calculator {
    
    private static final String OPERATORS = "+-*/^"; // Final string meaning that the string cannot be changed also needs to be capitalized so it functions

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //Initializes the scanner

        System.out.println("Enter your equation:");

        String equation = sc.nextLine(); //scanner to get input from next line
        double result = evaluate(equation); //evaluates the equaiton typed into the scanner

        System.out.println("Result: " + result);
    }

    private static double evaluate(String equation) {
        Stack<Double> operands = new Stack<>(); //operands initialized as a new stack  
        Stack<Character> operators = new Stack<>(); //operators initialized as a new stack
        StringBuilder sb = new StringBuilder(); //initializes sb as a string builder becauses its faster and uses less memomory

        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i); //character c is set to be the value of the array equation at the point i

            if (Character.isDigit(c) || c == '.') { //Checks if the digit in c is equal to a period 
                sb.append(c); //appends the string at array value c
            } 
            else if (OPERATORS.indexOf(c) != -1) { //Gets the index of c in the string OPERATORS
                double operand = Double.parseDouble(sb.toString());
                sb.setLength(0); //sets lenght of sb to 0

                while (!operators.empty() && pemdas(c, operators.peek())) { // returns the value of the top ("front") of the collection without removing the element from the collection
                    double b = operands.pop(); //removes elements that do not follow rules of pemdas
                    double a = operands.pop(); //sets the doubles b and a to the popped elements of operands
                    operands.push(applyOperator(a, b, operators.pop()));
                }

                operators.push(c); //pushes c to the op of the stack
                operands.push(operand); //pushes value of operand to the top of the stack
            } 
            else if (c == '(') {
                operators.push(c); //pushes element c to the front of the stack
            } 
            else if (c == ')') { //[pushes elemet c to the front of the stack of operations because it is an operation inside of parenthesis
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

        return operands.pop(); //returns operands 
    }

    private static boolean pemdas(char op1, char op2) {// PEMDAS operation order
        if (op2 == '(' || op2 == ')') {//Parenthesis come first
            return false;
        } 
        else if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) { //multiplication and division valued second and addition and subtraciton valued third
            return false;
        } 
        else if (op1 == '^' && (op2 == '*' || op2 == '/' || op2 == '+' || op2 == '-')) { //exponent pemdas
            return false;
        } 
        else {
            return true;
        }
    }

    private static double applyOperator(double a, double b, char op) {
        switch (op) {
            case '+': // if + detected then add b to a
                return a + b;
            case '-': // if + detected then subtract b to a
                return a - b;
            case '*': // if + detected then multiply a by b
                return a * b;
            case '/': // if + detected then divide a by b
                return a / b;
            case '^': // if ^ detected then a is raised to the power of b
                return Math.pow(a, b);
            default: // if an illegal operator is detected then throw an illegal argument exception
                throw new IllegalArgumentException("Invalid operator: " + op); // illegal argument exception being thrown indicates an illegal argument inside of the code
        }
    }
}
