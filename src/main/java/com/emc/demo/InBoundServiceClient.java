package com.emc.demo;

import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.emc.demo.domain.FarmerDetails;

@Component
public class InBoundServiceClient {

  private static Random rnd = new Random();

  @Async("workExecutor")
  public void simulateRegistrationMC() throws InterruptedException {
    while (true) {
      InBoundService boundService = new InBoundService();
      FarmerDetails farmer = getFarmerDetails();
      boundService.handleInboundMC(farmer);
      Thread.sleep(2 * 1000);
    }
  }

  @Async("workExecutor")
  public void simulateContentMC() throws InterruptedException {
    while (true) {
      InBoundService boundService = new InBoundService();
      FarmerDetails farmer = getFarmerDetails();
      farmer.setFarmerNew(false);
      boundService.handleInboundMC(farmer);
      Thread.sleep(2 * 1000);
    }
  }

  private FarmerDetails getFarmerDetails() {
    FarmerDetails details = new FarmerDetails();
    details.setFarmerNew(rnd.nextBoolean());
    if (!details.isFarmerNew())
      details.setFarmerPartiallyRegistered(rnd.nextBoolean());
    details.setOBDIVREnabled(rnd.nextBoolean());
    details.setSupportedCircle(rnd.nextBoolean());
    return details;
  }

}
