import java.util.*;

/**
 * Associative array of key-value pairs.
 * @author Aaron Howe
 * @version Java 11
 */
public class MyDictionary {

    private final MyStack stack;
    private final Stack<Map<Object, Object>> dictionaryStack;
    private final Map<Map<Object, Object>, Integer> capacity;
    // boolean flag for scope switching (dynamic by default)
    private boolean defaultScope = true;
    private Runnable procedure = null;
    // storage for variables when in lexical scope
    private final Map<Runnable, Map<Object, Object>> staticDictionary;

    /**
     * Constructor.
     */
    public MyDictionary(MyStack stack) {
        this.stack = stack;
        this.dictionaryStack = new Stack<>();
        this.capacity = new HashMap<>();
        this.staticDictionary = new HashMap<>();
        Map<Object, Object> tempDict = new HashMap<>();
        dictionaryStack.push(tempDict);
        // starting temporary dictionary with a default capacity of 32
        capacity.put(tempDict, 32);
    }

    /**
     * Instantiates an empty dictionary defined with an initial default capacity of 32,
     * and is pushed onto the operand stack.
     * @throws InputMismatchException when dictionary capacity isn't a number or is negatively signed.
     */
    public void dict() {
        Object currentCap = stack.pop();
        // check if the capacity is a number
        if (currentCap instanceof Number) {
            // convert capacity from Number to int
            int newCap = ((Number) currentCap).intValue();
            // check if the capacity value is negatively signed
            if (newCap < 0) {
                throw new InputMismatchException("Dictionary capacity cannot be negative.");
            }
            // initialize new dictionary and add it to the operand stack with a default capacity
            Map<Object, Object> dictionary = new HashMap<>();
            capacity.put(dictionary, newCap);
            stack.push(dictionary);
        } else {
            throw new InputMismatchException("Dictionary capacity must be a number.");
        }
    }

    /**
     * Counts the number of key-value pairs stored in the dictionary.
     * @return Count.
     * @throws InputMismatchException when there is no dictionary.
     */
    public int length() {
        Object dictionary = stack.pop();
        if (!(dictionary instanceof Map)) {
            throw new InputMismatchException("Dictionary Not Found.");
        }
        Map<?, ?> dict = (Map<?, ?>)dictionary;
        return dict.size();
    }

    /**
     * Defines the capacity of a given dictionary.
     * @throws InputMismatchException when there is no capacity or the dictionary is type casted incorrectly.
     */
    public void maxLength() {
        Object currentDictionary = stack.pop();
        // check that the object popped from the stack is of type Map
        if (currentDictionary instanceof Map) {
            // initialzie new dictionary with state of popped dictionary
            Map<Object, Object> dictionary = (Map<Object, Object>) currentDictionary;
            Integer cap = capacity.get(dictionary);
            if (cap == null) {
                throw new InputMismatchException("Missing dictionary capacity.");
            }
            stack.push(cap);
        } else {
            throw new InputMismatchException("Dictionary must be of type Map.");
        }
    }

    /**
     * Pushes the dictionary onto a dictionary stack.
     * @throws InputMismatchException when the dictionary type cast is incorrect.
     */
    public void begin() {
        Object dictionary = stack.pop();
        if (dictionary instanceof Map) {
            dictionaryStack.push((Map<Object, Object>) dictionary);
        } else {
            throw new InputMismatchException("Dictionary must be of type Map.");
        }
    }

    /**
     * Removes a dictionary from the top of the dictionary stack.
     * @throws EmptyStackException when the stack is empty.
     */
    public void end() {
        if (dictionaryStack.size() > 0) {
            dictionaryStack.pop();
        } else {
            throw new EmptyStackException();
        }
    }

    /**
     * Defines a key to be associated with a value to create a key-value pair
     * in the dictionary.
     * @throws EmptyStackException when the stack doesn't contain at least two elements.
     */
    public void def() {
        if (stack.count() <  2) {
            throw new EmptyStackException();
        }
        Object value = stack.pop();
        Object key = stack.pop();
        // if we've enabled lexical scoping, go here
        if (!defaultScope && value instanceof Runnable) {
            Map<Object, Object> originals = new HashMap<>();
            for (Map<Object, Object> dict : dictionaryStack) {
                originals.putAll(dict);
            }
            // populate lexical dictionary with the first instances of defined variables
            staticDictionary.put((Runnable)value, originals);
        }
        // fetch current dictionary and get its capacity
        Map<Object, Object> dictionary = dictionaryStack.peek();
        Integer cap = capacity.get(dictionary);
        // checking if the cap has been reached
        if (cap != null && dictionary.size() >= cap && !dictionary.containsKey(key)) {
            throw new IllegalArgumentException("Reached Maximum Capacity.");
        }
        dictionary.put(key, value);
    }

    /**
     * Helper function for def, specifically for its test case.
     * @return a value associated with a key.
     */
    public Object defHelper(Object key) {
        if (defaultScope) {
            for (int i = dictionaryStack.size() - 1; i >= 0; i--) {
                Object value = dictionaryStack.get(i).get(key);
                if (value != null) {
                    return value;
                }
            }
        // if we've enabled lexical scoping, go here
        } else {
            Runnable proc = getProc();
            if (proc != null && staticDictionary.containsKey(proc)) {
                // fetch the value originally set to a given variable
                Object value = staticDictionary.get(proc).get(key);
                if (value != null) {
                    return value;
                }
            } 
            return dictionaryStack.peek().get(key);
        }
        return null;
    }

    /**
     * Procedure Setter Function.
     * @param proc Procedure in execution.
     */
    public void setProc(Runnable proc) {
        procedure = proc;
    }

    /**
     * Procedure Getter Function.
     * @return Procedure in execution.
     */
    public Runnable getProc() {
        return procedure;
    }

    /**
     * Returns the status of the default scope used by the interpreter for condition evaluation.
     * @return True if the interpreter is using dynamic scopingS
     */
    public boolean getScope() {
        return defaultScope;
    }

    /**
     * Sets the scope used by the interpreter to be dynamic.
     */
    public void dyn() {
        defaultScope = true;
    }

    /**
     * Sets the scope used by the interpreter to be lexical (static).
     */
    public void lex() {
        defaultScope = false;
    }
}