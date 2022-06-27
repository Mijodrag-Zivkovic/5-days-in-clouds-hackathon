package main.cloud.resources;

import main.cloud.LocalStorage;
import main.cloud.model.Action;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/actions")
public class ActionsResource {
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void addAction(ArrayList<Action> actions, @Context final HttpServletResponse response){

//        for(Action action : actions)
//        {
//            System.out.println(action.getUserId());
//            System.out.println(action.getActionType());
//            System.out.println(action.getServiceType());
//            System.out.println(action.getTimestamp());
//            System.out.println(action.getPayloadSizeMb());
//            System.out.println();
//        }
        LocalStorage.getInstance().addActions(actions);
        //LocalStorage.getInstance().actionsById("1");


//        response.setStatus(HttpServletResponse.SC_OK);
//        try {
//            response.flushBuffer();
//        }catch(Exception e){}

    }
}