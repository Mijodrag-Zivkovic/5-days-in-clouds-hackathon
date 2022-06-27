package main.cloud.filter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.cloud.model.Action;
import main.cloud.model.enums.ServiceType;
import org.glassfish.jersey.message.internal.ReaderWriter;
import org.glassfish.jersey.server.ContainerException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

@Provider
public class ActionFilter implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = containerRequestContext.getEntityStream();
        final StringBuilder b = new StringBuilder();
        try {
            if (in.available() > 0) {
                ReaderWriter.writeTo(in, out);

                byte[] requestEntity = out.toByteArray();
                toGson(b, requestEntity, containerRequestContext);

                containerRequestContext.setEntityStream(new ByteArrayInputStream(requestEntity));
            }
            //return containerRequestContext;
        } catch (IOException ex) {
            throw new ContainerException(ex);
        }

    }
    private void toGson(StringBuilder b, byte[] entity, ContainerRequestContext containerRequestContext) throws IOException {
        if (entity.length == 0)
            return;
        b.append(new String(entity)).append("\n");
        //System.out.println("#### Intercepted Entity ####");
        //System.out.println(b.toString());
        Gson gson = new Gson();
        ArrayList<Action> actions = new ArrayList<>();
        Type listType = new TypeToken<ArrayList<Action>>() {}.getType();
        actions = gson.fromJson(String.valueOf(b),listType);
        for(Action action : actions)
        {
//            System.out.println(action.getUserId());
//            System.out.println(action.getActionType());
//            System.out.println(action.getServiceType());
//            System.out.println(action.getTimestamp());
//            System.out.println(action.getPayloadSizeMb());
//            System.out.println("kraj");
            if(!validateUserId(action.getUserId()))
            {
                containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            }
            else if(!validateService(action.getServiceType()))
            {
                containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            }
            else if(!validateActionType(action.getServiceType(), action.getActionType()))
            {
                containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            }
            else if(!validateTimeStamp(action.getTimestamp()))
            {
                containerRequestContext.abortWith(Response.status(Response.Status.BAD_REQUEST).build());
            }
        }

    }
    private boolean validateUserId(String userId)
    {
        if(userId==null || userId.equals("") )
        {
            return false;
        }
        else
        {
            try{
                int id = Integer.parseInt(userId);
                if(id>=0)
                    return true;
                else
                    return false;
            }catch(NumberFormatException nfe){
                return false;
            }
        }
    }
    private boolean validateService(String service)
    {
        if(service==null)
            return false;
        for(ServiceType serviceType : ServiceType.values())
        {
            if(service.equals(serviceType.toString()) && !service.equals("NETWORK"))
                return true;
        }
        return false;
    }

    public boolean validateTimeStamp(String timeStamp) {

        if(timeStamp==null)
            return false;
        try{
            double d = Double.parseDouble(timeStamp);
            return true;
        }catch(NumberFormatException nfe){
            return false;
        }
    }

    public boolean validateActionType(String serviceType, String actionType)
    {
        if(actionType==null)
            return false;
        if(serviceType.equals("FUNC"))
        {
            if(actionType.equals("EXEC"))
                return true;
        }
        else if(serviceType.equals("DB"))
        {
            if(actionType.equals("INSERT") || actionType.equals("SELECT") || actionType.equals("SOFT_DELETE"))
                return true;
        }
        else if(serviceType.equals("OBJECT_STORAGE"))
        {
            if(actionType.equals("PUT") || actionType.equals("GET") || actionType.equals("SOFT_DELETE"))
                return true;
        }
        else if(serviceType.equals("VM"))
        {
            if(actionType.equals("START") || actionType.equals("STOP"))
                return true;
        }
        return false;
    }

}