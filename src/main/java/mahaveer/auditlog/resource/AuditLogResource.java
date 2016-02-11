package mahaveer.auditlog.resource;

import mahaveer.auditlog.Audit;
import mahaveer.auditlog.model.AuditLogPayload;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by qxw121 on 2/10/16.
 */
@RestController
@RequestMapping(value = "/audit")
public class AuditLogResource {

    @Audit
    @RequestMapping(value = "/log", method = RequestMethod.POST)
    public
    @ResponseBody
    String log(@RequestBody AuditLogPayload loginRequest) {
        return "success";
    }

    @RequestMapping(value = "/log-generic", method = RequestMethod.POST)
    public
    @ResponseBody
    String logGeneric(@RequestBody Map<String,String> auditLogPayload) {
        return "success";
    }

}
