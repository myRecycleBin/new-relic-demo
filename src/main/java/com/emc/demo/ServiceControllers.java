package com.emc.demo;

import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/service1")
public class ServiceControllers {

  public static boolean healthy = true;

  @RequestMapping("/service1")
  public String service1() {
    if (true)
      branch_1();

    return "service1 doing fine !!";

  }

  private void branch_1() {

    System.out.println("ServiceControllers.branch_1()");

  }

  @RequestMapping("/service2")
  public String service2() {
    if (!healthy)
      throw new ConversionNotSupportedException(null, String.class, new Throwable());
    else
      return "service2 doing fine !! doing fine";

  }

  @RequestMapping("/service3")
  public String service3() {
    if (!healthy)
      throw new TypeMismatchException("abc", String.class);
    else
      return "service3 doing fine !!doing fine";

  }

  @RequestMapping("/changeHealth")
  public String changeHealth() {
    healthy = !healthy;

    return "Health status changed to >>" + healthy;

  }

}
