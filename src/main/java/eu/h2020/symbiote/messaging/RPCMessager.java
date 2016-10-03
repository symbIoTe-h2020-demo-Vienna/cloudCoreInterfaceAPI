package eu.h2020.symbiote.messaging;

import com.google.gson.Gson;
import eu.h2020.symbiote.model.RegistrationObject;
import eu.h2020.symbiote.model.RegistrationObjectType;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RPCMessager {

    public static String sendMessage(String body, RegistrationObjectType type, String parentID) {
        System.out.println("Trying to send the message - rpcCall() triggered....");
        String response = "";
        try {
            RPCClient rpcClient = new RPCClient();
            RegistrationObject object = new RegistrationObject(body, type, parentID);
            Gson gson = new Gson();
            String objectJson = gson.toJson(object);
            response = rpcClient.rpcCall(objectJson);
            System.out.println("[MSG]()()()()()()()()()()()()()()()()()()() \nReceived message from RPC:\n"
                    + response
                    + "\n[MSG]()()()()()()()()()()()()()()()()()()()\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
