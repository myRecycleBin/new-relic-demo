package com.emc.demo.aspects;


//@Aspect
//@Component("abc")

public class MetricsAspect {/*
                             * 
                             * 
                             * 
                             * private final Meter requests =
                             * Metrics.newMeter(ServiceControllers.class, "requests", "requests",
                             * TimeUnit.SECONDS); private final Meter branch =
                             * Metrics.newMeter(ServiceControllers.class, "branch", "branch",
                             * TimeUnit.SECONDS); private final Meter unknown =
                             * Metrics.newMeter(ServiceControllers.class, "unknown", "unknown",
                             * TimeUnit.SECONDS);
                             * 
                             * 
                             * 
                             * 
                             * 
                             * @Pointcut("execution(* com.emc.demo.ServiceControllers.service1(..))")
                             * 
                             * public void methodService1() {}
                             * 
                             * @After("execution(* com.emc.demo.ServiceControllers.service1(..))")
                             * public void logAfter(JoinPoint joinPoint) {
                             * 
                             * System.out.println("getStatus invoked !");
                             * System.out.println("******");
                             * 
                             * requests.mark();
                             * 
                             * }
                             * 
                             * 
                             * @Pointcut("execution(private * * (..))")
                             * 
                             * public void anyPrivateMethod() {}
                             * 
                             * 
                             * @Pointcut("execution(public * * (..))")
                             * 
                             * public void anyPublicMethod() {}
                             * 
                             * 
                             * 
                             * //@After(
                             * "execution(private com.emc.demo.ServiceControllers.branch_1(..))")
                             * 
                             * @After("anyPrivateMethod()") public void logAfterbranch1(JoinPoint
                             * joinPoint) {
                             * 
                             * System.out.println(joinPoint.getSignature().getName());
                             * 
                             * System.out.println("branch invoked !"); System.out.println("******");
                             * 
                             * branch.mark();
                             * 
                             * }
                             * 
                             * 
                             * 
                             * @Pointcut("anyPrivateMethod() || methodService1()")
                             * 
                             * public void unKnownMethod() {}
                             * 
                             * 
                             * @After(
                             * "!methodService1() && anyPublicMethod() && !within(MetricsAspect) && within(ServiceControllers)"
                             * ) public void logForUnknown(JoinPoint joinPoint) {
                             * 
                             * System.out.println("MetricsAspect.logForUnknown()"); unknown.mark();
                             * 
                             * }
                             */
}