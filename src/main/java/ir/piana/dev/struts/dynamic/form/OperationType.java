package ir.piana.dev.struts.dynamic.form;

public enum OperationType {
    UNKNOWN("unknown"),
    QUERY("query"),
    QUERY_STRING("query-string"),
    FUNCTION("function");

    private String name;

    OperationType(String name) {
        this.name = name;
    }

    public static OperationType fromName(String name) {
        for (OperationType operationType : OperationType.values()) {
            if (operationType.name.equals(name))
                return operationType;
        }
        return UNKNOWN;
    }
}
