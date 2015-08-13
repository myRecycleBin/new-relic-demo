package com.emc.demo;

import java.util.Random;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.emc.demo.domain.FarmerDetails;

@Service
public class InBoundService {
  private static Random rnd = new Random();
  private int delayInMin = 2;

  @Async
  public void handleInboundMC(FarmerDetails farmer) throws InterruptedException {
    if (farmer.isFarmerNew() && farmer.isSupportedCircle() && farmer.isOBDIVREnabled()) {
      initiateIVRReg();
      return;
    }

    if (farmer.isFarmerNew() && !farmer.isSupportedCircle()) {
      // Simple record and return doing nothing
      return;
    }

    if (farmer.isFarmerNew() && farmer.isSupportedCircle() && farmer.isOBDIVREnabled()) {
      // Simple record and return doing nothing
      return;
    }

    if (farmer.isFarmerPartiallyRegistered()) {
      reinitiateIVRReg();
    }

    if (!farmer.isFarmerNew()) {
      initiateIVRContent();
    }
  }

  private void initiateIVRContent() throws InterruptedException {
    int sleeptime = rnd.nextInt(delayInMin * 60 * 1000);
    System.out.println("InBoundService.initiateIVRContent() for " + sleeptime);
    Thread.sleep(sleeptime);
  }

  private void reinitiateIVRReg() throws InterruptedException {
    int sleeptime = rnd.nextInt(delayInMin * 60 * 1000);
    System.out.println("InBoundService.reinitiateIVRReg() for " + sleeptime);
    Thread.sleep(sleeptime);
  }

  private void initiateIVRReg() throws InterruptedException {
    int sleeptime = rnd.nextInt(delayInMin * 60 * 1000);
    System.out.println("InBoundService.initiateIVRReg() for " + sleeptime);
    Thread.sleep(sleeptime);
  }

}
