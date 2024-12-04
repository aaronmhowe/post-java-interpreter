import java.util.*;

/**
 * Entry point for `PostJava`, a command-line interpreter for PostScript written in the Java programming language. 
 * This application handles not all, but a strong set of operations for PostScript.
 * @Author Aaron Howe
 * @version Java 11
 */
public class Main {

    // class references
    private static MyStack stack;
    private static MyArithmetic arith;
    private static MyDictionary dict;
    private static MyString string;
    private static MyBoolean bool;
    private static MyFlowControl flow;
    private static MyIO io;
    // string builder for function procedures
    private static StringBuilder procedure = null;
    // stores curly brace tokens
    private static int symbolCount;

    /**
     * Main application entry method for PostJava.
     * @param args String array of command-line arguments.
     */
    public static void main(String[] args) {
        stack = new MyStack();
        arith = new MyArithmetic(stack);
        bool = new MyBoolean(stack);
        dict = new MyDictionary(stack);
        flow = new MyFlowControl(stack);
        io = new MyIO(stack);
        string = new MyString(stack);

        Scanner sc = new Scanner(System.in);
        boolean invoked = true;
        
        while (invoked && !flow.terminated()) {
            if (procedure != null) {
                System.out.print(">");
            } else {
                // stores a symbol representation of the scope being used by the interpreter
                String scope = dict.getScope() ? "D" : "L";
                // display the prompt with scope symbol and stack countj
                System.out.printf("[PJ:%s > %d] ", scope, stack.count());
            }
            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                continue;
            }
            try {
                procedureBuilder(input);
            } catch (Exception e) {
                System.err.println("Exception: " + e.getMessage());
                procedure = null;
                symbolCount = 0;
            }
        }
        sc.close();
    }

    /**
     * Local helper function to for constructing, parsing, and executing procedural operations.
     * @param input User input.
     */
    private static void procedureBuilder(String input) {
        String[] tokens = input.trim().split("\\s+");
        for (String token : tokens) {
            if (procedure != null) {
                // identifies an entry brace, increases the count
                if (token.equals("{")) {
                    symbolCount++;
                // identifies an exit brace, decreases the count
                } else if (token.equals("}")) {
                    symbolCount--;
                }
                procedure.append(token).append(" ");
                if (symbolCount == 0) {
                    // instantiate a string of the procedure's tokens
                    String buildProc = procedure.toString().trim();
                    // execute procedure
                    Runnable executeProc = new RunProcedure(buildProc, dict);
                    stack.push(executeProc);
                    procedure = null;
                }
                continue;
            }
            // populates the StringBuilder object when an entry brace is first identified
            if (token.equals("{")) {
                procedure = new StringBuilder();
                symbolCount = 1;
                procedure.append(token).append(" ");
                continue;
            }
            scannerHelper(token);
        }
    }

    /**
     * Local helper function to process command-line input from the user.
     * @param command command-line input.
     */
    private static void scannerHelper(String command) {
        // for input as variable or function def
        if (command.startsWith("/")) {
            stack.push(command.substring(1));
            return;
        }
        // for input as numbers
        try {
            if (command.contains(".")) {
                stack.push(Double.parseDouble(command));
            } else {
                stack.push(Integer.parseInt(command));
            }
            return;
        } catch (NumberFormatException e) {
            // fetch function/variable
            Object element = dict.defHelper(command);
            if (element != null) {
                // function execution
                if (element instanceof Runnable) {
                    ((Runnable)element).run();
                    return;
                }
                // push variable if not function
                stack.push(element);
                return;
            }
        }

        // for input as PostScript command operations
        switch (command.toLowerCase()) {
            case "exch":
                stack.exch();
                break;
            case "pop":
                stack.pop();
                break;
            case "copy":
                stack.copy();
                break;
            case "dup":
                stack.dup();
                break;
            case "clear":
                stack.clear();
                break;
            case "count":
                stack.count();
                break;
            case "add":
                arith.add();
                break;
            case "sub":
                arith.sub();
                break;
            case "mul":
                arith.mul();
                break;
            case "div":
                arith.div();
                break;
            case "idiv":
                arith.iDiv();
                break;
            case "mod":
                arith.mod();
                break;
            case "abs":
                arith.abs();
                break;
            case "neg":
                arith.neg();
                break;
            case "ceiling":
                arith.ceiling();
                break;
            case "floor":
                arith.floor();
                break;
            case "round":
                arith.round();
                break;
            case "sqrt":
                arith.sqrt();
                break;
            case "dict":
                dict.dict();
                break;
            case "maxlength":
                dict.maxLength();
                break;
            case "begin":
                dict.begin();
                break;
            case "end":
                dict.end();
                break;
            case "def":
                dict.def();
                break;
            case "dyn":
                dict.dyn();
                break;
            case "lex":
                dict.lex();
                break;
            // length contains extra logic to account for its ambiguity 
            case "length":
                Object operand = stack.peek();
                // call length on dict if the operand at the top of the stack is a Map
                if (operand instanceof Map) {
                    dict.length();
                // or call length on string if the operand at the top of the stack is a String
                } else if (operand instanceof String) {
                    string.length();
                } else {
                    throw new IllegalArgumentException("length can only be called on strings or dictionaries");
                }
                break;
            case "get":
                string.get();
                break;
            case "getinterval":
                string.getInterval();
                break;
            case "putinterval":
                string.putInterval();
                break;
            case "eq":
                bool.isEqual();
                break;
            case "ne":
                bool.notEqual();
                break;
            case "gt":
                bool.greaterThan();
                break;
            case "lt":
                bool.lessThan();
                break;
            case "ge":
                bool.greaterThanOrEqualTo();
                break;
            case "le":
                bool.lessThanOrEqualTo();
                break;
            case "and":
                bool.and();
                break;
            case "or":
                bool.or();
                break;
            case "not":
                bool.not();
                break;
            case "true":
                bool.psTrue();
                break;
            case "false":
                bool.psFalse();
                break;
            case "if":
                flow.ifCondition();
                break;
            case "ifelse":
                flow.ifElseCondition();
                break;
            case "for":
                flow.forLoop();
                break;
            case "repeat":
                flow.repeat();
                break;
            case "quit":
                flow.quit();
                break;
            case "print":
                io.print();
                break;
            case "=":
                io.printElement();
                break;
            case "==":
                io.printArray();
                break;
            default:
                // string literal
                if (command.startsWith("(") && command.endsWith(")")) {
                    stack.push(command.substring(1, command.length() - 1));
                } else {
                    throw new IllegalArgumentException("Error: Command Not Recognized.");
                }
        }
    }

    /**
     * Inner-class that servers to implement the Runnable interface for instantiation and procedural execution.
     */
    private static class RunProcedure implements Runnable {

        private final String[] tokens;
        private final MyDictionary dict;

        /**
         * Constructor
         * @param buildProc String procedure in execution.
         * @param dict Dictionary stack.
         */
        public RunProcedure(String buildProc, MyDictionary dict) {
            this.tokens = buildProc.substring(1, buildProc.length() - 1).trim().split("\\s+");
            this.dict = dict;
        }
        /**
         * Heart of the thread, defining the code for execution at its start.
         */
        @Override
        public void run() {
            Runnable originalProc = dict.getProc();
            dict.setProc(this);
            try {
                for (String token : tokens) {
                    scannerHelper(token);
                }
            } finally {
                dict.setProc(originalProc);
            }
        }
    }
}