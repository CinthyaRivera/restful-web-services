package com.fsv.rest.webservices.restfulwebservices.exception;

import java.util.List;

public class ErrorResponse {
    private boolean hasError;
    private List<ErrorDetail> errors;
    private Object data;

    public ErrorResponse(boolean hasError, List<ErrorDetail> errors, Object data) {
        this.hasError = hasError;
        this.errors = errors;
        this.data = data;
    }

    public boolean isHasError() {
        return hasError;
    }

    public void setHasError(boolean hasError) {
        this.hasError = hasError;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorDetail> errors) {
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static class ErrorDetail {
        private String code;
        private String description;

        public ErrorDetail(String code, String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
