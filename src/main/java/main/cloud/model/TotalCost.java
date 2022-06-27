package main.cloud.model;

import java.math.BigDecimal;
import java.util.ArrayList;

public class TotalCost {
    private BigDecimal totalCosts;
    private ArrayList<CostsPerService> costsPerService;

    public TotalCost() {
        costsPerService = new ArrayList<>();

    }

    public BigDecimal getTotalCosts() {
        return totalCosts;
    }

    public void setTotalCosts(BigDecimal totalCosts) {
        this.totalCosts = totalCosts;
    }

    public ArrayList<CostsPerService> getCostsPerService() {
        return costsPerService;
    }

    public void setCostsPerService(ArrayList<CostsPerService> costsPerService) {
        this.costsPerService = costsPerService;
    }
}
