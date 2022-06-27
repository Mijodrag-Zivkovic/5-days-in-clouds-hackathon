package main.cloud.services;

import main.cloud.LocalStorage;
import main.cloud.model.Action;
import main.cloud.model.CostsPerService;
import main.cloud.model.Service;
import main.cloud.model.TotalCost;
import main.cloud.model.enums.ServiceType;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServicesCalculationService {

    private String userId;
    private String untilDate;
    private ArrayList<Action> actions;
    private HashMap<String, Service> services;
    private List<String> requiredServices;
    private boolean vmStarted;


    public ServicesCalculationService(String userId, String untilDate, List<String> requiredServices) {
        this.userId = userId;
        this.untilDate = untilDate;
        this.actions = LocalStorage.getInstance().actionsById(userId);
        this.services = new HashMap<>();
        this.requiredServices = requiredServices;
        if(requiredServices.isEmpty())
        {
            for(ServiceType serviceType : ServiceType.values())
                requiredServices.add(serviceType.toString());
        }
        for(ServiceType serviceType : ServiceType.values())
        {
            services.put(serviceType.toString(),new Service(serviceType.toString()));
        }
        this.vmStarted=false;
    }


    public void calculateActionFUNC(String payloadSize)
    {
        Service service=services.get("FUNC");
        if(service.getFreeInvocations()>0)
        {
            service.setFreeInvocations(service.getFreeInvocations()-1);
        }
        else
        {
            int currentInvoStep = service.getCurrentInvoStep();
            if(service.getCurrentInvoStep()==0)
            {
                service.setPrice(service.getPrice().add(new BigDecimal("0.01")));
            }
            currentInvoStep+=1;
            if(currentInvoStep>9)
            {
                currentInvoStep=0;
            }
            service.setCurrentInvoStep(currentInvoStep);
        }
        try{
            if(payloadSize==null)
                payloadSize="0";
            BigDecimal payload = new BigDecimal(payloadSize);
            BigDecimal helper = new BigDecimal("0");
            helper = helper.add(service.getFreeData());
            helper = helper.subtract(payload);
            if(helper.compareTo(BigDecimal.ZERO)>0)
            {
                service.setFreeData(helper);
            }
            else
            {
                helper = helper.multiply(new BigDecimal("-0.01"));
                service.setPriceNetwork(service.getPriceNetwork().add(helper));
                service.setFreeData(BigDecimal.ZERO);
            }
        }catch(NumberFormatException nfe){
            System.out.println("bad mb");
        }
    }

    public void calculateActionDB(String actionType, String payloadSize)
    {
        Service service=services.get("DB");
        if(service.getFreeInvocations()>0)
        {
            service.setFreeInvocations(service.getFreeInvocations()-1);
        }
        else
        {
            int currentInvoStep = service.getCurrentInvoStep();
            if(service.getCurrentInvoStep()==0)
            {
                service.setPrice(service.getPrice().add(new BigDecimal("0.01")));
            }
            currentInvoStep+=1;
            if(currentInvoStep>9)
            {
                currentInvoStep=0;
            }
            service.setCurrentInvoStep(currentInvoStep);
        }
        if(actionType.equals("INSERT"))
        {
            try{
                if(payloadSize==null)
                    payloadSize="0";
                BigDecimal payload = new BigDecimal(payloadSize);
                BigDecimal helper = new BigDecimal("0");
                helper = helper.add(service.getFreeData());
                helper = helper.subtract(payload);
                if(helper.compareTo(BigDecimal.ZERO)>0)
                {
                    service.setFreeData(helper);
                }
                else
                {
                    helper = helper.multiply(new BigDecimal("-0.01"));
                    service.setPrice(service.getPrice().add(helper));
                    service.setFreeData(BigDecimal.ZERO);
                }
            }catch(NumberFormatException nfe){
                System.out.println("bad mb");
            }
        }
    }

    public void calculateActionOS(String actionType, String payloadSize)
    {
        Service service=services.get("OBJECT_STORAGE");
        if(actionType.equals("PUT")||actionType.equals("GET"))
        {
            try{
                if(payloadSize==null)
                    payloadSize="0";
                BigDecimal payload = new BigDecimal(payloadSize);
                BigDecimal helper = new BigDecimal("0");
                helper = helper.add(service.getFreeData());
                helper = helper.subtract(payload);
                if(helper.compareTo(BigDecimal.ZERO)>0)
                {
                    service.setFreeData(helper);
                }
                else
                {
                    helper = helper.multiply(new BigDecimal("-0.01"));
                    if(actionType.equals("GET"))
                        service.setPriceNetwork(service.getPriceNetwork().add(helper));
                    else
                        service.setPrice(service.getPrice().add(helper));
                    service.setFreeData(BigDecimal.ZERO);
                }
            }catch(NumberFormatException nfe){
                System.out.println("bad mb");
            }
        }
    }

    public void calculateActionVM(String actionType, String timeStamp)
    {
        Service service=services.get("VM");
        if(actionType.equals("START"))
        {
            if(!this.vmStarted)
            {
                service.setVmTimeOfStarting(timeStamp);
                this.vmStarted=true;
            }
        }
        else
        {
            if(this.vmStarted)
            {
                try{
                    BigDecimal start = new BigDecimal(service.getVmTimeOfStarting());
                    System.out.println(start);
                    BigDecimal stop = new BigDecimal(timeStamp);
                    System.out.println(stop);
                    BigDecimal helper = stop.subtract(start);
                    System.out.print("helper ");
                    System.out.println(helper);
                    helper = service.getFreeTime().subtract(helper);
                    if(helper.compareTo(BigDecimal.ZERO)>0)
                    {
                        service.setFreeTime(helper);
                    }
                    else
                    {
                        helper = helper.multiply(new BigDecimal("-0.01"));
                        service.setPrice(service.getPrice().add(helper));
                        service.setFreeTime(BigDecimal.ZERO);
                    }
                }catch(NumberFormatException nfe){
                    System.out.println("bad mb");
                }
                this.vmStarted=false;
            }
        }
        System.out.println(service.getPrice());
    }

    public TotalCost calculate()
    {
        //float totalPrice=0;


        if(actions!=null)
        {
            for(Action action : actions)
            {
                BigDecimal timeStamp = new BigDecimal(action.getTimestamp());
                BigDecimal untilDate = new BigDecimal(this.untilDate);
                if(timeStamp.compareTo(untilDate)<=0)
                {

                    if(action.getServiceType().equals("FUNC"))
                    {
                        calculateActionFUNC(action.getPayloadSizeMb());
                    }
                    else if(action.getServiceType().equals("DB"))
                    {
                        calculateActionDB(action.getActionType(), action.getPayloadSizeMb());
                    }
                    else if(action.getServiceType().equals("OBJECT_STORAGE"))
                    {
                        calculateActionOS(action.getActionType(), action.getPayloadSizeMb());
                    }
                    else
                    {
                        calculateActionVM(action.getActionType(), action.getTimestamp());
                    }
                }
            }
        }
        TotalCost totalCost = new TotalCost();

        BigDecimal networkCost = new BigDecimal("0");
        for(ServiceType serviceType : ServiceType.values() )
        {

            if(serviceType.toString().equals("NETWORK"))
            {
                //totalCost.getCostsPerService().add(new CostsPerService(serviceType.toString(),networkCost));
                services.get(serviceType.toString()).setPrice(networkCost);
            }
            else if(serviceType.toString().equals("VM") && this.vmStarted)
            {
                BigDecimal helper = new BigDecimal(this.services.get("VM").getVmTimeOfStarting());
                helper = new BigDecimal(this.untilDate).subtract(helper);
                helper = helper.multiply(new BigDecimal("0.01"));
                helper = services.get("VM").getPrice().add(helper);
                services.get("VM").setPrice(helper);
            }
            else
            {
                networkCost=networkCost.add(services.get(serviceType.toString()).getPriceNetwork());
                //totalCost.getCostsPerService().add(new CostsPerService(serviceType.toString(),services.get(serviceType.toString()).getPrice()));
            }
        }

        BigDecimal costSummed = new BigDecimal("0");
        for(ServiceType serviceType : ServiceType.values())
        {
            CostsPerService costsPerService = new CostsPerService(serviceType.toString(),this.services.get(serviceType.toString()).getPrice());
            costSummed=costSummed.add(costsPerService.getCost());
            if(this.requiredServices.contains(serviceType.toString()))
            {
                totalCost.getCostsPerService().add(costsPerService);
            }
        }
        totalCost.setTotalCosts(costSummed);
        return totalCost;
    }
}
