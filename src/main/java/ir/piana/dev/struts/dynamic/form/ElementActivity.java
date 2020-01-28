package ir.piana.dev.struts.dynamic.form;

import java.util.List;

public class ElementActivity {
    String type;
    String name;
    List<String> permissions;
    List<Operation> operations;
    MessageDef successMessage;
    MessageDef failureMessage;

    public ElementActivity() {
    }

    public ElementActivity(String type, String name, List<String> permissions, List<Operation> operations,
                           MessageDef successMessage, MessageDef failureMessage) {
        this.type = type;
        this.name = name;
        this.permissions = permissions;
        this.operations = operations;
        this.successMessage = successMessage;
        this.failureMessage = failureMessage;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<Operation> getOperations() {
        return operations;
    }

    public MessageDef getSuccessMessage() {
        return successMessage;
    }

    public MessageDef getFailureMessage() {
        return failureMessage;
    }

    public static class Operation implements Comparable<Operation> {
        int order;
        OperationType operationType;
        String name;
        String resultAttributeName;

        public Operation(int order, OperationType operationType, String name, String resultAttributeName) {
            this.order = order;
            this.operationType = operationType;
            this.name = name;
            this.resultAttributeName = resultAttributeName;
        }

        public int getOrder() {
            return order;
        }

        public OperationType getOperationType() {
            return operationType;
        }

        public String getName() {
            return name;
        }

        public String getResultAttributeName() {
            return resultAttributeName;
        }

        @Override
        public int compareTo(Operation operation) {
            return this.order - operation.order;
        }
    }

    public static class MessageDef {
        String messageKey;
        List<String> parameters;

        public MessageDef() {
        }

        public MessageDef(String messageKey, List<String> parameters) {
            this.messageKey = messageKey;
            this.parameters = parameters;
        }

        public String getMessageKey() {
            return messageKey;
        }

        public List<String> getParameters() {
            return parameters;
        }
    }
}
