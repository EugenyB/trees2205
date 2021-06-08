public interface TreeType {
    Object getValue(String data);

    TreeType DoubleTree = Double::parseDouble;

    TreeType IntegerTree = Integer::parseInt;

    TreeType StringTree = s->s;

    default boolean isValidType(String data) {
        try {
            getValue(data);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
