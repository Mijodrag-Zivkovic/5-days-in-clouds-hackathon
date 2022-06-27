package main.cloud.resources;

import main.cloud.LocalStorage;
import main.cloud.model.Action;
import main.cloud.model.Service;
import main.cloud.model.TotalCost;
import main.cloud.model.enums.ServiceType;
import main.cloud.services.ServicesCalculationService;

import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Path("/user")
public class UserResource {
    @GET
    @Path("/{userId}/costs")
    @Produces("application/json")
    public TotalCost calculateCost(@PathParam("userId") String userId, @QueryParam("untilDate") String untilDate, @QueryParam("serviceTypes") List<String> serviceTypes){
//        System.out.println(userId);
//        System.out.println(untilDate);
//        ArrayList<Action> actions = LocalStorage.getInstance().actionsById(userId);
//        //ArrayList<Service> services = new ArrayList<>();
//        HashMap<String,Service> services = new HashMap<>();
//        for(ServiceType serviceType : ServiceType.values())
//        {
//            services.put(serviceType.toString(),new Service(serviceType.toString()));
//        }
//        for(Action action : actions)
//        {
//
//        }
        ServicesCalculationService calculator = new ServicesCalculationService(userId, untilDate, serviceTypes);
        TotalCost totalCost = calculator.calculate();
        return totalCost;
    }
}
