package mahaveer.auditlog.resource;

/**
 * Created by qxw121 on 2/10/16.
 */
public class LoginResponse {
    String responseStatus;

    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }
}
