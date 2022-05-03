package com.jedromz.petclinic.aop.annotation;

import com.jedromz.petclinic.model.technical.EndpointUsage;
import com.jedromz.petclinic.repository.technical.EndpointUsageRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Aspect
@Component
@RequiredArgsConstructor
public class MetricsAspect {

    private final EndpointUsageRepository endpointUsageRepository;

    @SneakyThrows
    @Around("@annotation(com.jedromz.petclinic.aop.annotation.Metric)")
    public Object logEndpointMetrics(ProceedingJoinPoint proceedingJoinPoint) {
        long start = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long end = System.currentTimeMillis();
        long time = end - start;

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        EndpointUsage endpointUsage = new EndpointUsage();
        endpointUsage.setExecutionTime(time);
        endpointUsage.setIpAddress(request.getRemoteAddr());
        endpointUsage.setMethodName(proceedingJoinPoint.getSignature().getName());
        endpointUsage.setExecutionDate(LocalDateTime.now());
        endpointUsageRepository.saveAndFlush(endpointUsage);
        return result;
    }
}
