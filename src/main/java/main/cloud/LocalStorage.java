package main.cloud;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import main.cloud.model.Action;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class LocalStorage {

    private static LocalStorage instance;
    private ConcurrentHashMap<String, ArrayList<Action>> storage;


    private LocalStorage()
    {
        storage = new ConcurrentHashMap<>();
    }

    public static LocalStorage getInstance() {
        if(instance == null){
            instance = new LocalStorage();
        }
        return instance;
    }

    public void addActions(ArrayList<Action> actions){
        for(Action action : actions)
        {
            String userId = action.getUserId();
            if(!storage.containsKey(userId))
            {
                storage.put(userId, new ArrayList<>());
            }
            storage.get(userId).add(action);
        }

    }

    public ArrayList<Action> actionsById(String id){
        return storage.get(id);
//        for(Action action : userActions)
//        {
//            System.out.println(action.getUserId());
//            System.out.println(action.getActionType());
//            System.out.println(action.getServiceType());
//            System.out.println(action.getTimestamp());
//            System.out.println(action.getPayloadSizeMb());
//        }
    }








}
