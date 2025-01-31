package Framework;

public class PatException extends Exception {
    private String className;
    private String methodName;

    public PatException(String message, String className, String methodName) {
        super(message);
        this.className = className;
        this.methodName = methodName;
    }

    @Override
    public String toString() {
        return "PatException: " + super.getMessage() +
               " in class: " + className +
               ", method: " + methodName;
    }
}
