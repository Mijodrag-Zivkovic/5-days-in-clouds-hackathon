package main.cloud.model;

import java.math.BigDecimal;

public class CostsPerService {
    String serviceType;
    BigDecimal cost;

    public CostsPerService(String serviceType, BigDecimal cost) {
        this.serviceType = serviceType;
        this.cost = cost;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }
}
