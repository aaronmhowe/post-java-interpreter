import java.util.*;

/**
 * Implements a string object.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyString {

    private final MyStack stack;

    /**
     * Constructor.
     */
    public MyString(MyStack stack) {
        this.stack = stack;
    }

    /**
     * Counts the number of character elements that make up a string object.
     * @return Count.
     * @throws InputMismatchException when the provided argument is not a String type.
     */
    public int length() {
        Object string = stack.pop();
        if (!(string instanceof String)) {
            throw new InputMismatchException("Type Error: length() requires an argument of type String.");
        }
        return ((String)string).length();
    }

    /**
     * Fetches a character element from the string.
     * @throws IndexOutOfBoundsException when the index position is outside the range of the length of the string.
     * @throws InputMismatchException when the provided argument for string is not type String, and the provided
     * argument for element is not a numeric type.
     */
    public void get() {
        Object element = stack.pop();
        Object string = stack.pop();
        if ((string instanceof String) && (element instanceof String)) {
            // we have to store the states of string and element to new explicit types
            String str = (String) string;
            int index = Integer.parseInt((String)element);
            // to check that they hold valid states
            if (index < 0 || index >= str.length()) {
                throw new IndexOutOfBoundsException();
            }
            stack.push(str.charAt(index));
        } else {
            throw new InputMismatchException();
        }
    }

    /**
     * Instantiates a string object whose state depends on a subsequence
     * of a an existing string.
     * @throws IndexOutOfBoundsException when the index position is outside the range of the length of the string.
     * @throws InputMisMatchException when the provided argument for string is not type String, and the provided
     * argument for element is not a numeric type.
     */
    public void getInterval() {
        Object length = stack.pop();
        Object element = stack.pop();
        Object string = stack.pop();
        if ((string instanceof String) && (element instanceof Integer) && (length instanceof Integer)) {
            String str = (String) string;
            int index = ((Number)element).intValue();
            int chars = ((Number)length).intValue();
            if (index < 0 || index + chars > str.length()) {
                throw new IndexOutOfBoundsException();
            }
            stack.push(str.substring(index, index + chars));
        } else {
            throw new InputMismatchException("Invalid Type.");
        }
    }

    /**
     * Replaces a subsequence of character elements in an existing string with 
     * the character elements of a new string, beginning at some specified index in
     * the older string.
     * @throws IndexOutOfBoundsException when the index position is outside the range of the length of the string.
     * @throws InputMismatchException when the provided argument for string is not type String, and the provided
     * argument for element is not a numeric type.
     */
    public void putInterval() {
        Object substring = stack.pop();
        Object element = stack.pop();
        Object string = stack.pop();
        if ((string instanceof String) && (element instanceof Integer) && (substring instanceof String)) {
            String str = (String) string;
            int index = ((Number)element).intValue();
            String sub = (String) substring;
            if (index < 0 || index + sub.length() > str.length()) {
                throw new IndexOutOfBoundsException();
            }
            // split original string from its first char to the specified index position
            stack.push(str.substring(0, index) 
            // concatenate specified substring onto first-half of split
            + sub
            // concatenate second-half of original string onto substring but only for remaining index positions from original string 
            + str.substring(index + sub.length()));
        } else {
            throw new InputMismatchException();
        }
    }
}