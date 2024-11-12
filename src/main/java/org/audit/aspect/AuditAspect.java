package org.audit.aspect;

import lombok.SneakyThrows;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.audit.annotation.Audit;
import org.audit.enums.EventType;
import org.audit.repository.AuditRepository;

import java.util.HashMap;
import java.util.Map;

/** Класс Аспектов - аудит действий пользователя с сохранением в бд */
@Aspect
public class AuditAspect {

    private static final String USER_ID = "idUser";

    private final AuditRepository auditRepository;

    public AuditAspect(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @SneakyThrows
    @Before("@annotation(audit)")
    public void logBefore(JoinPoint joinPoint, Audit audit) {
        var params = getMethodParams(joinPoint);
        var description = createDescription(params, audit.eventType());
        Long idUser = Long.valueOf(params.get(USER_ID));
        auditRepository.save(idUser, description);
    }

    private String createDescription(Map<String, String> params, EventType eventType) {
        return eventType.getDescription() + " with arguments = " + params.keySet();
    }

    private Map<String, String> getMethodParams(JoinPoint joinPoint) {
        var args = joinPoint.getArgs();
        var signature = (MethodSignature) joinPoint.getSignature();
        var parameterNames = signature.getParameterNames();

        Map<String, String> namedArguments = new HashMap<>();
        for (int i = 0; i < args.length; i++) {
            var arg = args[i];
            String param = null;
            if(arg instanceof Long) {
                param = String.valueOf(arg);
            }
            else if(arg instanceof String) {
                param = (String) arg;
            }
            namedArguments.put(parameterNames[i], param);
        }
        return namedArguments;
    }

}
