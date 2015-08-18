package com.emc.demo.aspects;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.util.StopWatch;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;

@Aspect
public class InBoundServiceMetricsAspect {
  MetricRegistry metricRegistry = new MetricRegistry();
  //Graphite graphite = new Graphite(new InetSocketAddress("localhost", 2003));
  Graphite graphite = new Graphite(new InetSocketAddress("192.168.5.20", 2023));
  
  GraphiteReporter reporter = GraphiteReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.MINUTES)
      .convertDurationsTo(TimeUnit.SECONDS).filter(MetricFilter.ALL).build(graphite);

  MetricRegistry aggRegistry = new MetricRegistry();
  GraphiteReporter aggReporter = GraphiteReporter.forRegistry(aggRegistry).convertRatesTo(TimeUnit.MINUTES)
      .convertDurationsTo(TimeUnit.SECONDS).filter(MetricFilter.ALL).build(graphite);

  /**
   * ConsoleReporter reporter =
   * ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.MINUTES)
   * .convertDurationsTo(TimeUnit.SECONDS).build();
   **/

  private final Meter requests = metricRegistry.meter("ff.inbound.missedcallcount");
  private final Meter initiateIVRContent = metricRegistry.meter("ff.inbound.missedcalls.contentcount");
  private final Meter reinitiateIVRReg = metricRegistry.meter("ff.inbound.missedcalls.registration.retry");
  private final Meter initiateIVRReg = metricRegistry.meter("ff.inbound.missedcalls.registration.new");
  private final Histogram ivrRegDuration = metricRegistry.histogram("ff.outbound.ivrreg.completed.duration");
  private final Counter missedCallCounter = metricRegistry.counter("ff.inbound.missedcall.counter");
  private final Gauge missedCallSum = aggRegistry.register(aggRegistry.name("ff.inbound.missedcall.total"),
      new Gauge<Integer>() {
        @Override
        public Integer getValue() {
          Counter c = metricRegistry.counter("ff.inbound.missedcall.counter");
          long count = c.getCount();
          c.dec(count);
          System.out.println("InBoundServiceMetricsAspect.enclosing_method()..count >>" + count);
          return (int) count;
        }
      });

  public InBoundServiceMetricsAspect() {
    reporter.start(30, TimeUnit.SECONDS);
    aggReporter.start(1, TimeUnit.MINUTES);
  }

  @After("execution(* com.emc.demo.InBoundService.handleInboundMC(..))")
  public void afterMCHandling(JoinPoint joinPoint) {
    System.out.println("---->");
    requests.mark();
    missedCallCounter.inc();
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