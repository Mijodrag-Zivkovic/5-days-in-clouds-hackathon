package main.cloud.model;

import javax.validation.constraints.*;


public class Action {

    private String userId;
    private String serviceType;
    private String actionType;
    private String timestamp;
    private String payloadSizeMb;

    public Action() {
    }

    public Action(String userId, String serviceType, String actionType, String timestamp, String payloadSizeMb) {
        this.userId = userId;
        this.serviceType = serviceType;
        this.actionType = actionType;
        this.timestamp = timestamp;
        this.payloadSizeMb = payloadSizeMb;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayloadSizeMb() {
        return payloadSizeMb;
    }

    public void setPayloadSizeMb(String payloadSizeMb) {
        this.payloadSizeMb = payloadSizeMb;
    }
}
