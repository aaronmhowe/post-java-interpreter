import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.*;

/**
 * Test class for the PostScript operations Stack, Arithmetic, Dictionary, String, Boolean
 * Flow Control, and Input/Output.
 * @author Aaron Howe
 * @version Java 11
 */
public class InterpreterTest {

    private MyStack stack;
    private MyArithmetic arith;
    private MyDictionary dictionary;
    private MyString string;
    private MyBoolean bool;
    private MyFlowControl flow;
    private MyIO io;

    /**
     * Builds the test environment.
     */
    @Before
    public void setUp() {
        stack = new MyStack();
        arith = new MyArithmetic(stack);
        dictionary = new MyDictionary(stack);
        string = new MyString(stack);
        bool = new MyBoolean(stack);
        flow = new MyFlowControl(stack);
        io = new MyIO(stack);
    }

    /**
     * Tests that the exch() function in MyStack swaps the top two elements on the stack.
     */
    @Test
    public void exchTest() {
        stack.push(1);
        stack.push(2);
        stack.exch();
        assertEquals(1, stack.pop());
        assertEquals(2, stack.pop());
    }

    /**
     * Tests that the copy() function in MyStack copies the elements in the operand stack and
     * pushes the copied elements back onto the stack.
     */
    @Test
    public void copyTest() {
        stack.push(1);
        stack.push(2);
        stack.push(3);
        // n = 3
        stack.push(3);
        stack.copy();
        assertEquals(6, stack.count());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(1, stack.pop());
    }

    /**
     * Tests that the dup() function in MyStack duplicates the element at the top of the operand stack
     * and pushes it back onto the stack.
     */
    @Test
    public void dupTest() {
        stack.push(1);
        stack.dup();
        assertEquals(2, stack.count());
        assertEquals(1, stack.pop());
        assertEquals(1, stack.pop());
    }

    /**
     * Tests that the clear() function in MyStack removes all elements in the operand stack.
     */
    @Test
    public void clearTest() {
        stack.push(1);
        stack.clear();
        assertEquals(0, stack.count());
    }

    /**
     * Tests that the add() function in MyArithmetic adds the two elements at the top of the
     * operand stack and pushes the result back onto the stack.
     */
    @Test
    public void addTest() {
        stack.push(2);
        stack.push(2);
        arith.add();
        assertEquals(4, stack.pop());
    }

    /**
     * Tests that the sub() function in MyArithmetic subtracts the element at the top of the stack from the next
     * element on the stack and pushes the result back onto the stack.
     */
    @Test
    public void subTest() {
        stack.push(4);
        stack.push(2);
        arith.sub();
        assertEquals(2, stack.pop());
    }

    /**
     * Tests that the mul() function in MyArithmetic multiplies the two elements at the top of the operand
     * stack and pushes the result back onto the stack.
     */
    @Test
    public void mulTest() {
        stack.push(2);
        stack.push(2);
        arith.mul();
        assertEquals(4, stack.pop());
    }

    /**
     * Tests that the div() function in MyArithmetic divides the element at the top of the operand stack from the next
     * element on the stack and pushes the result back onto the stack.
     */
    @Test
    public void divTest() {
        stack.push(4);
        stack.push(2);
        arith.div();
        assertEquals(2.0, (Double)stack.pop(), 0.001);
    }

    @Test
    public void divNumerLessThanDenomTest() {
        stack.push(2);
        stack.push(4);
        arith.div();
        assertEquals(0.5, (Double)stack.pop(), 0.001);
    }

    /**
     * Tests that an attempt to divide by zero results in the proper exception clause.
     */
    @Test(expected = ArithmeticException.class)
    public void divByZeroExceptionTest() {
        stack.push(2);
        stack.push(0);
        arith.div();
    }

    /**
     * Tests that the iDiv() function in MyArithmetic divides the element at the top of the operand stack from the next
     * element on the stack and pushes the result as type int back onto the stack.
     */
    @Test
    public void iDivTest() {
        stack.push(4);
        stack.push(2);
        arith.iDiv();
        assertEquals(2, stack.pop());
    }

    /**
     * Tests that an attempt to divide by zero results in the proper exception clause.
     */
    @Test(expected = ArithmeticException.class)
    public void iDivByZeroExceptionTest() {
        stack.push(2);
        stack.push(0);
        arith.iDiv();
    }

    /**
     * Tests that an attempt to call iDiv() with an operand of type double results in the proper exception clause.
     */
    @Test(expected = ArithmeticException.class)
        public void iDivNonIntegerTest() {
        stack.push(4.1);
        stack.push(2);
        arith.iDiv();
    }

    /**
     * Tests that the mod() function in MyArithmetic returns the remainder of a dividend that cannot be divided
     * evenly and pushes the remainder back onto the operand stack.
     */
    @Test
    public void modDivisorGreaterThanDividendTest() {
        stack.push(5);
        stack.push(2);
        arith.mod();
        assertEquals(1, stack.pop());
    }

    /**
     * Tests that when attempting to perform the mod() function on a dividend that is the smaller than
     * the divisor, the result is the value of the dividend, and pushed back onto the operand stack.
     */
    @Test
    public void modDivisorLessThanDividendTest() {
        stack.push(2);
        stack.push(5);
        arith.mod();
        assertEquals(2, stack.pop());
    }

    /**
     * Tests that the abs() function in MyArithmetic computes and pushes the absolute value of a given operand
     * onto the operand stack.
     */
    @Test
    public void absTest() {
        stack.push(-2);
        arith.abs();
        assertEquals(2, stack.pop());
    }

    /**
     * Tests that the neg() function in MyArithmetic converts a given positively signed operand to its negation and pushes
     * the result back onto the operand stack.
     */
    @Test
    public void negTest() {
        stack.push(2);
        arith.neg();
        assertEquals(-2, stack.pop());
    }

    /**
     * Tests that the ceiling() function in MyArithmetic rounds a floating operand up to the nearest whole number and pushes
     * the result back onto the operand stack.
     */
    @Test
    public void ceilingTest() {
        stack.push(2.4);
        arith.ceiling();
        assertEquals(3, stack.pop());
    }

    /**
     * Tests that the floor() function in MyArithmetic rounds a floating operand down to the nearest whole number and pushes
     * the result back onto the operand stack.
     */
    @Test
    public void floorTest() {
        stack.push(2.6);
        arith.floor();
        assertEquals(2, stack.pop());
    }

    /**
     * Tests that the round() function in MyArithmetic rounds floating operands to the nearest whole number and pushes the
     * result back onto the operand stack.
     */
    @Test
    public void roundTest() {
        stack.push(2.4);
        arith.round();
        assertEquals(2, stack.pop());

        stack.push(2.6);
        arith.round();
        assertEquals(3, stack.pop());
    }

    /**
     * Tests that the sqrt() function in MyArithmetic accurately computes the square root of a given operand and pushes the
     * result back onto the operand stack.
     */
    @Test
    public void sqrtTest() {
        stack.push(4);
        arith.sqrt();
        assertEquals(2.0, (Double)stack.pop(), 0.001);
    }

    /**
     * Tests that an attempt at computing the square root of a negatively signed operand results in the proper exception clause.
     */
    @Test(expected = ArithmeticException.class)
    public void sqrtNegativeTest() {
        stack.push(-4);
        arith.sqrt();
    }

    /**
     * Tests that the dict() function in MyDictionary successfully constructs a dictionary that
     * functions like a Java HashMap.
     */
    @Test
    public void dictTest() {
        // capacity
        stack.push(5);
        dictionary.dict();
        Object dict = stack.pop();
        assertTrue(dict instanceof Map);
    }

    /**
     * Tests that the length() function in MyDictionary accurately counts the number of key-value pairs
     * in a dictionary.
     */
    @Test
    public void dictLengthTest() {
        stack.push(5);
        dictionary.dict();
        Map<Object, Object> dict = (Map<Object, Object>) stack.pop();
        dict.put("One", 1);
        dict.put("Two", 2);
        stack.push(dict);
        assertEquals(2, dictionary.length());
    }

    /**
     * Tests that the maxLength() function in MyDictionary properly defines the maximum capacity of the dictionary.
     */
    @Test
    public void dictMaxLengthTest() {
        stack.push(5);
        dictionary.dict();
        Object dict = stack.pop();
        stack.push(dict);
        dictionary.maxLength();
        assertEquals(5, stack.pop());
    }

    /**
     * Tests that the def() function in MyDictionary successfully defines a key-value pair.
     */
    @Test
    public void defTest() {
        stack.push(5);
        dictionary.dict();
        dictionary.begin();
        stack.push("key");
        stack.push(2);
        dictionary.def();
        assertEquals(2, dictionary.defHelper("key"));
    }

    @Test
    public void endTest() {
        stack.push(5);
        dictionary.dict();
        dictionary.begin();
        dictionary.end();
    }

    /**
     * Tests that the length() function in MyString accurately counts the number of characters
     * in a string pushed to the stack.
     */
    @Test
    public void stringLengthTest() {
        stack.push("Test");
        assertEquals(4, string.length());
    }

    /**
     * Tests that the get() function in MyString successfully retrieves the character of a string at a
     * specified index position.
     */
    @Test
    public void stringGetTest() {
        stack.push("Test");
        stack.push("3");
        string.get();
        assertEquals('t', stack.pop());
    }

    /**
     * Tests that the getInterval() function in MyString successfully constructs a new string based on specified
     * index values of an existing string object.
     */
    @Test
    public void getIntervalTest() {
        stack.push("Test");
        stack.push(0);
        stack.push(2);
        string.getInterval();
        assertEquals("Te", stack.pop());
    }

    /**
     * Tests that the putInterval() function in MyString successfully replaces characters at specified index positions
     * of an existing string with characters defined in a new string.
     */
    @Test
    public void putIntervalTest() {
        stack.push("Test");
        stack.push(1);
        stack.push("ro");
        string.putInterval();
        assertEquals("Trot", stack.pop());
    }

    /**
     * Tests that the isEqual() function in MyBoolean returns true when called on two equal values.
     */
    @Test
    public void isEqualTest() {
        stack.push(2.0);
        stack.push(2);
        bool.isEqual();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests the isEqual() function in MyBoolean on two identical strings.
     */
    @Test
    public void isEqualStringTest() {
        stack.push("Test");
        stack.push("Test");
        bool.isEqual();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests that the notEqual() function in MyBoolean returns true when performed on two non-equal values.
     */
    @Test
    public void notEqualTest() {
        stack.push(1);
        stack.push(2);
        bool.notEqual();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests the notEqual() function in MyBoolean on two different strings.
     */
    @Test
    public void notEqualStringTest() {
        stack.push("Test");
        stack.push("Case");
        bool.notEqual();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests that the greaterThan() function in MyBoolean returns true when comparing a larger value to a smaller value.
     */
    @Test
    public void greaterThanTest() {
        stack.push(2);
        stack.push(1);
        bool.greaterThan();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests that the lessThan() function in MyBoolean returns true when comparing a smaller value to a larger value.
     */
    @Test
    public void lessThanTest() {
        stack.push(1);
        stack.push(2);
        bool.lessThan();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests that the greaterThanOrEqualTo() function in MyBoolean returns true when comparing a larger value to a smaller
     * value, and returns true when comparing two equal values.
     */
    @Test
    public void greaterThanEqualToTest() {
        stack.push(2);
        stack.push(1);
        bool.greaterThanOrEqualTo();
        assertTrue((Boolean)stack.pop());
        stack.push(2);
        stack.push(2);
        bool.greaterThanOrEqualTo();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests that the lessThanOrEqualTo() function in MyBoolean returns true when comparing a smaller value to a larger value,
     * and returns true when comparing two identical values.
     */
    @Test
    public void lessThanOrEqualToTest() {
        stack.push(1);
        stack.push(2);
        bool.lessThanOrEqualTo();
        assertTrue((Boolean)stack.pop());
        stack.push(2);
        stack.push(2);
        bool.lessThanOrEqualTo();
        assertTrue((Boolean)stack.pop());
    }

    /**
     * Tests that the and() function in MyBoolean returns true when pairing two conditions of the same truth value.
     */
    @Test
    public void andTest() {
        stack.push(true);
        stack.push(true);
        bool.and();
        assertTrue((Boolean)stack.pop());
        stack.push(true);
        stack.push(false);
        bool.and();
        assertFalse((Boolean)stack.pop());
        stack.push(5);
        stack.push(3);
        bool.and();
        assertEquals(1, ((Number)stack.pop()).intValue());
    }

    /**
     * Tests that the or() function in MyBoolean returns true when pairing two conditions of opposite truth value.
     */
    @Test
    public void orTest() {
        stack.push(true);
        stack.push(false);
        bool.or();
        assertTrue((Boolean)stack.pop());
        stack.push(false);
        stack.push(false);
        bool.or();
        assertFalse((Boolean)stack.pop());
        stack.push(5);
        stack.push(3);
        bool.or();
        assertEquals(7, ((Number)stack.pop()).intValue());
    }

    /**
     * Tests that the not() function in MyBoolean returns true when called on a condition that returns false.
     */
    @Test
    public void notTest() {
        stack.push(false);
        bool.not();
        assertTrue((Boolean)stack.pop());
        stack.push(2);
        bool.not();
        assertEquals(~2, ((Number)stack.pop()).intValue());
    }

    /**
     * Tests that the psTrue() and psFalse() functions in MyBoolean push their respective boolean true and false.
     */
    @Test
    public void trueFalseTest() {
        bool.psTrue();
        assertTrue((Boolean)stack.pop());
        bool.psFalse();
        assertFalse((Boolean)stack.pop());
    }

    /**
     * Tests that the ifCondition() function in MyFlowControl executes when its condition is met.
     */
    @Test
    public void ifTest() {
        final boolean[] condition = {false};
        stack.push(true);
        stack.push((Runnable)(() -> condition[0] = true));
        flow.ifCondition();
        assertTrue(condition[0]);
    }

    /**
     * Tests that the ifElseCondition() function in MyFlowControl executes when a higher-level if condition fails,
     * and its condition is met.
     */
    @Test
    public void ifElseTest() {
        final boolean[] condition = {false};
        stack.push(false);
        stack.push((Runnable)(() -> {}));
        stack.push((Runnable)(() -> condition[0] = true));
        flow.ifElseCondition();
        assertTrue(condition[0]);
    }

    /**
     * Tests that the forLoop() function in MyFlowControl iteratively executes a summation of integers based on its
     * defined looping condition.
     */
    @Test
    public void forTest() {
        final int[] number = {0};
        // i = 0;
        stack.push(0);
        // i++;
        stack.push(1);
        // i < 4;
        stack.push(4);
        stack.push((Runnable)(() -> number[0] += (Integer)stack.pop()));
        flow.forLoop();
        assertEquals(10, number[0]);
    }

    /**
     * Tests that the repeat() function in MyFlowControl executes a procedure a specified number of times.
     */
    @Test
    public void repeatTest() {
        final int[] iter = {0};
        stack.push(5);
        stack.push((Runnable)(() -> iter[0]++));
        flow.repeat();
        assertEquals(5, iter[0]);
    }

    /**
     * Tests that the quit() function in MyFlowControl closes the interpreter.
     */
    @Test
    public void interpreterQuitsTest() {
        flow.quit();
        assertTrue(flow.terminated());
    }

    /**
     * Tests that the print() function in MyIO prints a given string to the standard output.
     */
    @Test
    public void printTest() {
        stack.push("Hello, World!");
        io.print();
    }

    /**
     * Tests that the printElement() function converts an operand to a string and prints it
     * to the standard output.
     */
    @Test
    public void printElementTest() {
        stack.push(1);
        io.printElement();
    }

    /**
     * Tests that the printArray() function builds a string display a structure's contents and prints it to
     * the standard output. 
     */
    @Test
    public void printArrayTest() {
        stack.push("Test");
        io.printArray();
    }
}