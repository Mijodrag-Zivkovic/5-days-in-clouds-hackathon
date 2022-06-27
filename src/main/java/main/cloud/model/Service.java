package main.cloud.model;

import java.math.BigDecimal;
import java.util.HashMap;

public class Service {
    private String serviceName;
    private int freeInvocations;
    private BigDecimal freeTime;
    private BigDecimal freeData;
    private int currentInvoStep;
    private BigDecimal price;
    private BigDecimal priceNetwork;
//    private boolean vmStarted;
    private String vmTimeOfStarting;

    public Service(String serviceName) {
        this.serviceName = serviceName;
        freeInvocations = 10;
        freeTime = new BigDecimal("36000");
        freeData = new BigDecimal("1024");
        currentInvoStep = 0;
        price = new BigDecimal("0");
        priceNetwork = new BigDecimal("0");
//        vmStarted=false;
        vmTimeOfStarting="";
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getFreeInvocations() {
        return freeInvocations;
    }

    public void setFreeInvocations(int freeInvocations) {
        this.freeInvocations = freeInvocations;
    }

    public BigDecimal getFreeTime() {
        return freeTime;
    }

    public void setFreeTime(BigDecimal freeTime) {
        this.freeTime = freeTime;
    }

    public BigDecimal getFreeData() {
        return freeData;
    }

    public void setFreeData(BigDecimal freeData) {
        this.freeData = freeData;
    }

    public int getCurrentInvoStep() {
        return currentInvoStep;
    }

    public void setCurrentInvoStep(int currentInvoStep) {
        this.currentInvoStep = currentInvoStep;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceNetwork() {
        return priceNetwork;
    }

    public void setPriceNetwork(BigDecimal priceNetwork) {
        this.priceNetwork = priceNetwork;
    }

//    public boolean isVmStarted() {
//        return vmStarted;
//    }
//
//    public void setVmStarted(boolean vmStarted) {
//        this.vmStarted = vmStarted;
//    }
//
    public String getVmTimeOfStarting() {
        return vmTimeOfStarting;
    }

    public void setVmTimeOfStarting(String vmTimeOfStarting) {
        this.vmTimeOfStarting = vmTimeOfStarting;
    }
}
