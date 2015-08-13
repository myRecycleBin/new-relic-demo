package com.emc.demo.aspects;

import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricRegistry;

@Aspect
public class InBoundServiceMetricsAspect {
  MetricRegistry metricRegistry = new MetricRegistry();
  ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.MINUTES)
      .convertDurationsTo(TimeUnit.SECONDS).build();

  private final Meter requests = metricRegistry.meter("ff.inbound.missedcallcount");
  private final Meter initiateIVRContent = metricRegistry.meter("ff.inbound.missedcalls.contentcount");
  private final Meter reinitiateIVRReg = metricRegistry.meter("ff.inbound.missedcalls.registration.retry");
  private final Meter initiateIVRReg = metricRegistry.meter("ff.inbound.missedcalls.registration.new");
  private final Histogram ivrRegDuration = metricRegistry.histogram("ff.outbound.ivrreg.completed.duration");

  public InBoundServiceMetricsAspect() {
    reporter.start(30, TimeUnit.SECONDS);
  }

  @After("execution(* com.emc.demo.InBoundService.handleInboundMC(..))")
  public void afterMCHandling(JoinPoint joinPoint) {
    System.out.println("---->");
    requests.mark();
  }

  @Around("execution(private * com.emc.demo.InBoundService.initiateIVRReg())")
  public Object aroundInitingIVRRegCall(ProceedingJoinPoint pjp) throws Throwable {
    System.out.println("///////>");    
    StopWatch stp = new StopWatch();
    stp.start();
    Object retVal = pjp.proceed();
    stp.stop();
    ivrRegDuration.update((int) stp.getTotalTimeSeconds());
    return retVal;
  }

  @After("execution(private * com.emc.demo.InBoundService.initiateIVRReg())")
  public void ivrReg() {
    System.out.println("++++++>");
    initiateIVRReg.mark();
  }

  @After("execution(private * com.emc.demo.InBoundService.reinitiateIVRReg())")
  public void ivrReReg() {
    System.out.println("=====>");
    reinitiateIVRReg.mark();
  }

  @After("execution(private * com.emc.demo.InBoundService.initiateIVRContent())")
  public void ivrCont() {
    System.out.println("XXXXXX>");
    initiateIVRContent.mark();
  }
}