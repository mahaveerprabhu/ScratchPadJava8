package mahaveer.auditlog.resource;

import mahaveer.auditlog.Audit;
import mahaveer.auditlog.model.AuditLogPayload;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by qxw121 on 2/10/16.
 */
@RestController
@RequestMapping(value = "/user")
@Audit
public class UserResource {

    //@Audit
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public
    @ResponseBody
    LoginResponse login(@RequestBody LoginRequest loginRequest, AuditLogPayload auditLog) {
        LoginResponse response = new LoginResponse();
        response.setResponseStatus("success");
        response.setToken("12345");
        auditLog.setBusinessEvent("UserId Login with class level Annotation");
        auditLog.setBusinessResult("Success");
        return response;
    }

    @RequestMapping(value = "/sureswipe", method = RequestMethod.POST)
    public
    @ResponseBody
    String sureswipeLogin(@RequestBody Map<String,String> sureSwipeLogin) {
        return "success";
    }

}
