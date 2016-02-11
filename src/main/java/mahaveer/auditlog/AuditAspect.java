package mahaveer.auditlog;

import mahaveer.auditlog.model.AuditLogPayload;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by qxw121 on 2/4/16.
 */
@Component
@Aspect
public class AuditAspect {
    private static final Logger log = LoggerFactory.getLogger(AuditAspect.class);

    //@Around("execution(public * mahaveer.auditlog.resource.*.*(..)) &&  @annotation( org.springframework.web.bind.annotation.RequestMapping)")
    //@Around("@annotation( auditAnnotation ) || @target(annotation)")
    @Around("execution(public * mahaveer.auditlog.resource.*.*(..)) && (@target( auditAnnotation ) || @annotation( auditAnnotation ))")
    public Object logAround(ProceedingJoinPoint joinPoint, Audit auditAnnotation) throws Throwable {

        log.debug("Aspect around method : " + joinPoint.getSignature().getName());
        Object[] args = joinPoint.getArgs();
        log.debug("arguments : " + Arrays.toString(args));

        final String methodName = joinPoint.getSignature().getName();

        final MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
        }
        Annotation[][] methodParamAnnotations = method.getParameterAnnotations();
        Class[] paramTypes = method.getParameterTypes();
        for(int i=0; i < paramTypes.length; i++){
            if(AuditLogPayload.class.equals(paramTypes[i])){
                if(args[i] == null){
                    log.debug("Instantiating AuditLogPayload for {}", methodName);
                    args[i] = new AuditLogPayload();
                }
            }
        }
        // RequestParam requestParamAnnotation = (RequestParam) methodParamAnnotations[0][0];
        Object result;
        log.debug("Around before is running!");
        try {
            result = joinPoint.proceed(args); //continue on the intercepted method
        }
        catch (Throwable t){
            log.error(t.getMessage(),t);
            throw t;
        }
        log.debug("Around after is running!");

        log.debug("******");
        return result;
    }
}
