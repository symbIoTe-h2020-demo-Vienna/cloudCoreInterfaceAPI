package eu.h2020.symbiote.messaging;

import com.google.gson.Gson;
import eu.h2020.symbiote.model.RegistrationObject;
import eu.h2020.symbiote.model.RegistrationObjectType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by mateuszl on 03.10.2016.
 */
public class RPCMessager {

    private static Log log = LogFactory.getLog(RPCMessager.class);

    public static String sendMessage(String body, RegistrationObjectType type, String parentID) {
        log.info("Trying to send the message - rpcCall() triggered...");
        String response = "";
        try {
            RPCClient rpcClient = RPCClient.getInstance();
            RegistrationObject object = new RegistrationObject(body, type, parentID);
            Gson gson = new Gson();
            String objectJson = gson.toJson(object);
            response = rpcClient.rpcCall(objectJson);
            log.info("[V] Received RPC-MSG : \n"
                    + response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public static void closeConnection() throws Exception {
        RPCClient rpcclient = RPCClient.getInstance();
        rpcclient.close();
    }
}
