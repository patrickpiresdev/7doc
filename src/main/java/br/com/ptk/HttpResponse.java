package br.com.ptk;

public class HttpResponse {
    private final int statusCode;
    private String body;
    private String error;

    public HttpResponse(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", body='" + body + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
