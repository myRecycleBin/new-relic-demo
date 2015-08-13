package com.emc.demo.domain;

public class FarmerDetails {

  public boolean isFarmerNew() {
    return farmerNew;
  }

  public void setFarmerNew(boolean farmerNew) {
    this.farmerNew = farmerNew;
  }

  public boolean isSupportedCircle() {
    return supportedCircle;
  }

  public void setSupportedCircle(boolean supportedCircle) {
    this.supportedCircle = supportedCircle;
  }

  public boolean isOBDIVREnabled() {
    return OBDIVREnabled;
  }

  public void setOBDIVREnabled(boolean oBDIVREnabled) {
    OBDIVREnabled = oBDIVREnabled;
  }

  public boolean isFarmerPartiallyRegistered() {
    return farmerPartiallyRegistered;
  }

  public void setFarmerPartiallyRegistered(boolean farmerPartiallyRegistered) {
    this.farmerPartiallyRegistered = farmerPartiallyRegistered;
  }

  private boolean farmerNew;

  private boolean supportedCircle;

  private boolean OBDIVREnabled;

  private boolean farmerPartiallyRegistered;
}
