package ptit.edu.vn.ltw.logging;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
@Slf4j
public class ServiceLogging {
    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceBean() {}

    @Around("serviceBean()")
    @SuppressWarnings("java:S2139")
    public Object aroundServiceMethod(ProceedingJoinPoint pjp) throws Throwable {
        // determine target class for logger (fall back to declaring type)
        Class<?> targetClass = (pjp.getTarget() != null)
                ? pjp.getTarget().getClass()
                : pjp.getSignature().getDeclaringType();

        Logger logger = LoggerFactory.getLogger(targetClass);

        Signature sig = pjp.getSignature();
        String methodName = sig.getName();
        Object[] args = pjp.getArgs();
        String logInput = loggingInput(args);

        try {
            logger.info("Enter: {}.{}() with args={}", targetClass.getSimpleName(), methodName, logInput);
            Object result = pjp.proceed();
            String logOutput = loggingOutput(result);
            logger.info("Exit : {}.{}() returned={}", targetClass.getSimpleName(), methodName, logOutput);

            return result;
        } catch (Exception ex) {
            logger.error("Exception in {}.{}() with args={}", targetClass.getSimpleName(), methodName, logInput, ex);
            throw ex;
        }
    }

    private String loggingInput(Object[] args) {
        if (args == null || args.length == 0) return "[]";
        try {
            return Arrays.deepToString(args);
        } catch (Exception ex) {
            log.error("Failed to stringify method arguments for logging", ex);
            return "[unprintable-args]";
        }
    }

    private String loggingOutput(Object o) {
        if (o == null) return "null";
        return String.valueOf(o);
    }

}
