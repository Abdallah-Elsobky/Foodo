package iti.student.foodo.features.utils;

public class ErrorUtils {
    public static String getErrorMessage(Throwable throwable) {
        String error = "Error";
        if (throwable instanceof java.io.IOException) {
            error = "No Internet Connection. Please check your network.";
        } else if (throwable instanceof retrofit2.HttpException) {
            retrofit2.HttpException httpException = (retrofit2.HttpException) throwable;
            int code = httpException.code();
            if (code == 401) {
                error = "Session expired. Please login again.";
            } else if (code >= 500) {
                error = "Server error. Please try again later.";
            } else {
                error = "Unexpected error occurred (Code: " + code + ")";
            }
        } else if (throwable instanceof com.google.gson.JsonSyntaxException) {
            error = "Data processing error. Please contact support.";
        } else {
            error = "An unexpected error occurred.";
        }
        return error;
    }
}
