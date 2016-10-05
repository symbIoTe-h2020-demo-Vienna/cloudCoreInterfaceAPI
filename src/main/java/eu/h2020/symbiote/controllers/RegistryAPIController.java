package eu.h2020.symbiote.controllers;

import com.google.gson.Gson;
import eu.h2020.symbiote.messaging.RPCMessager;
import eu.h2020.symbiote.model.RegistrationObjectType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by mateuszl on 22.09.2016.
 */
@CrossOrigin
@RestController
public class RegistryAPIController {

    public static Log log = LogFactory.getLog(RegistryAPIController.class);

    private String server = "localhost";
    private int port = 8200;

    @RequestMapping(value = "/cloud_api/platforms", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addPlatform(@RequestBody String body, HttpMethod method, HttpServletRequest request)
            throws URISyntaxException {
        URI uri = new URI("http", null, server, port, request.getRequestURI(), request.getQueryString(), null);

        // for debug
        log.info(uri);
        log.info("method: " + method);
        log.info("body:\n" + body + "\n");

        String response = RPCMessager.sendMessage(body, RegistrationObjectType.PLATFORM, null);

        return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/cloud_api/platforms/{platform_id}/resources", method = RequestMethod.POST,
            headers = "Accept=application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addResource(@PathVariable(value = "platform_id") String platformId,
                                              @RequestBody String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException {
        URI uri = new URI("http", null, server, port, request.getRequestURI(), request.getQueryString(), null);

        //for debug
        log.info(uri);
        log.info("method: " + method);
        log.info("body:\n" + body + "\n");

        String receivedObject = RPCMessager.sendMessage(body, RegistrationObjectType.SENSOR, platformId);
        Gson gson = new Gson();
        String response = gson.toJson(receivedObject);

        return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
    }

}