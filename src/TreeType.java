/**
 * Implementation of Strategy Pattern:
 * Implements method for check element is Valid for this TreeType
 * Create abstract method for parsing string data to type of tree element
 */
public interface TreeType {
    /**
     * Parse element type from string representation
     * @param data string representation
     * @return extracted (parsed) value
     */
    Object getValue(String data);

    /**
     * Tree that contains Doubles
     * Overrides getValue for Double::parseDouble
     */
    TreeType DoubleTree = Double::parseDouble;

    /**
     * Tree that contains Integers
     * Overrides getValue for Integer::parseInt
     */
    TreeType IntegerTree = Integer::parseInt;

    /**
     * Tree that contains strings
     * Overrsides getValue, so it returns initial string
     */
    TreeType StringTree = s->s;

    /**
     * Check if value passed to tree is valid for it's type
     * @param data value to store in tree
     * @return true if data is valid, false - if not valid
     */
    default boolean isValidType(String data) {
        try {
            getValue(data);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
