import java.util.*;

/**
 * Implements arithmetic logic for values stored in an operand stack.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyArithmetic {

    private final MyStack stack;
    
    /**
     * Default constructor.
     */
    public MyArithmetic(MyStack stack) {
        this.stack = stack;
    }

    /**
     * Helper function fetches an operand from the operand stack.
     * @return Operand from the stack.
     * @throws InputMismatchException when the given operand is not a numerical value.
     */
    public double operand() {
        Object operand = stack.pop();
        // check if the operand is numeric and return as such
        if (operand instanceof Number) {
            return ((Number) operand).doubleValue();
        }
        throw new InputMismatchException("Operand must be a number.");
    }

    /**
     * Alternative helper function fetches an int operand from the operand stack.
     * @return Operand from the stack as type int.
     * @throws InputMismatchException when the given operand is not an integer.
     */
    public int iOperand() {
        Object operand = stack.pop();
        if (operand instanceof Number) {
            double num = ((Number) operand).doubleValue();
            if (num == Math.floor(num) && !Double.isInfinite(num)) {
                return ((Number) operand).intValue();
            }
            else {
                throw new InputMismatchException();
            }
        }
        throw new InputMismatchException();
    }

    /**
     * Additional helper function that determines if operand values are numeric or explicit integers so arithmetic operations compute a result
     * dependent on the data types of their operands.
     * @param op1 operand operand
     * @param op2 Second operand
     * @return True if the operands are integers, false if the operands are doubles.
     */
    private boolean isInt(Number op1, Number op2) {
        double val1 = op1.doubleValue();
        double val2 = op2.doubleValue();
        return val1 == Math.floor(val1) && val2 == Math.floor(val2) && !Double.isInfinite(val1) && !Double.isInfinite(val2);
    }

    /**
     * Evaluates the combination of two values.
     */
    public void add() {
        Number first = operand();
        Number second = operand();
        // check if the operands are integers (no decimals) and return the sum as an integer
        if (isInt(second, first)) {
            stack.push(second.intValue() + first.intValue());
        // if the operands are not both integer types, return the sum as double
        } else {
            stack.push(second.doubleValue() + first.doubleValue());
        }
    }

    /**
     * Evaluates the difference of two values.
     */
    public void sub() {
        Number first = operand();
        Number second = operand();
        if (isInt(second, first)) {
            stack.push(second.intValue() - first.intValue());
        } else {
            stack.push(second.doubleValue() - first.doubleValue());
        }
    }

    /**
     * Evaluates the product of two values.
     */
    public void mul() {
        Number first = operand();
        Number second = operand();
        if (isInt(second, first)) {
            stack.push(second.intValue() * first.intValue());
        } else {
            stack.push(second.doubleValue() * first.doubleValue());
        }
    }

    /**
     * Evaluates the quotient of two values.
     * @throws ArithmeticException when there is a division by zero.
     */
    public void div() {
        Number first = operand();
        if (first.doubleValue() == 0) {
            throw new ArithmeticException("Undefined");
        }
        Number second = operand();
        double quotient = second.doubleValue() / first.doubleValue();
        stack.push(Double.valueOf(quotient));
    }

    /**
     * Evaluates the quotient of two integer values.
     */
    public void iDiv() {
        Object first = stack.pop();
        if (!(first instanceof Number) || ((Number)first).doubleValue() != ((Number)first).intValue()) {
            throw new ArithmeticException("Operand should be an integer type.");
        }
        int divisor = ((Number)first).intValue();
        if (divisor == 0) {
            throw new ArithmeticException("Undefined");
        }
        Object second = stack.pop();
        if (!(second instanceof Number) || ((Number)second).doubleValue() != ((Number)second).intValue()) {
            throw new ArithmeticException("Operand should be an integer type.");
        }
        int dividend = ((Number)second).intValue();
        stack.push(dividend / divisor);
    }

    /**
     * Evaluates the remainder after the quotient of two values.
     * @throws ArithmeticException when there is a division by zero.
     */
    public void mod() {
        Object first = stack.pop();
        if (!(first instanceof Number) || ((Number)first).doubleValue() != ((Number)first).intValue()) {
            throw new ArithmeticException("Operand should be an integer type.");
        }
        int divisor = ((Number)first).intValue();
        if (divisor == 0) {
            throw new ArithmeticException("Undefined");
        }
        Object second = stack.pop();
        if (!(second instanceof Number) || ((Number)second).doubleValue() != ((Number)second).intValue()) {
            throw new ArithmeticException("Operand should be an integer type.");
        }
        int dividend = ((Number)second).intValue();
        stack.push(dividend % divisor);
    }

    /**
     * Computes the absolute value of a given operand.
     * @throws InputMismatchException when the provided operand is not a numeric type.
     */
    public void abs() {
        Object operand = stack.pop();
        // if operand is type Integer
        if (operand instanceof Integer) {
            stack.push(Math.abs((Integer)operand));
        // if operand is type Double
        } else if (operand instanceof Double) {
            stack.push(Double.valueOf(Math.abs((Double)operand)));
        // if operand is type Number
        } else if (operand instanceof Number) {
            double num = ((Number)operand).doubleValue();
            // if Number is a whole number, cast to int
            if (num == Math.floor(num) && !Double.isInfinite(num)) {
                stack.push(Math.abs((int)num));
            // otherwise cast to double
            } else {
                stack.push(Double.valueOf(Math.abs(num)));
            }
        } else {
            throw new InputMismatchException("Operand should be a numeric type.");
        }
    }

    /**
     * Converts a positively signed operand to a negative.
     * @throws InputMismatchException when the provided operand is not a numeric type.
     */
    public void neg() {
        Object operand = stack.pop();
        // if operand is type Integer
        if (operand instanceof Integer) {
            stack.push(-(Integer)operand);
        // if operand is type Double
        } else if (operand instanceof Double) {
            stack.push(Double.valueOf(-(Double)operand));
        // if operand is type Number
        } else if (operand instanceof Number) {
            double num = ((Number)operand).doubleValue();
            // if Number is a whole number, cast to int
            if (num == Math.floor(num) && !Double.isInfinite(num)) {
                stack.push(-(int)num);
            // otherwise cast to double
            } else {
                stack.push(Double.valueOf(-num));
            }
        } else {
            throw new InputMismatchException("Operand should be a numeric type.");
        }
    }

    /**
     * Rounds a given operand `up` to the nearest whole number.
     */
    public void ceiling() {
        Number operand = operand();
        // if the operand is an integer, then it is already at its whole number ceiling
        if (operand instanceof Integer) {
            stack.push(operand.intValue());
        } else {
            // cast the result of .ceil() to an integer
            stack.push((int)Math.ceil(operand.doubleValue()));
        }
    }

    /**
     * Rounds a given operand `down` to the nearest whole number.
     */
    public void floor() {
        Number operand = operand();
        if (operand instanceof Integer) {
            stack.push(operand.intValue());
        } else {
            stack.push((int)Math.floor(operand.doubleValue()));
        }
    }

    /**
     * Rounds a given operand to the nearest whole number.
     */
    public void round() {
        Number operand = operand();
        if (operand instanceof Integer) {
            stack.push(operand.intValue());
        } else {
            stack.push((int)Math.round(operand.doubleValue()));
        }
    }

    /**
     * Computes the square root of a given operand.
     * @throws ArithmeticException when a given operand is negatively signed.
     */
    public void sqrt() {
        Number operand = operand();
        double num = operand.doubleValue();
        // checking if the operand's value is negative
        if (num < 0) {
            throw new ArithmeticException("Negatively signed values cannot be square rooted.");
        }
        double rooted = Math.sqrt(num);
        stack.push(Double.valueOf(rooted));
    }
}