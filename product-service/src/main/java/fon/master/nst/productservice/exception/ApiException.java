package fon.master.nst.productservice.exception;

public class ApiException extends RuntimeException {

    private String errorMessage;

    private int status;

    private String statusCodeName;

    public ApiException(String errorMessage, int status, String statusCodeName) {
        this.errorMessage = errorMessage;
        this.status = status;
        this.statusCodeName = statusCodeName;

    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatusCodeName() {
        return statusCodeName;
    }

    public void setStatusCodeName(String statusCodeName) {
        this.statusCodeName = statusCodeName;
    }
}
