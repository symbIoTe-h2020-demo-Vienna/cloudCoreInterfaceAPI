package eu.h2020.symbiote.controllers;

import eu.h2020.symbiote.messaging.RPCMessager;
import eu.h2020.symbiote.model.RegistrationObjectType;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by mateuszl on 22.09.2016.
 */
@CrossOrigin
@RestController
public class PlatformController {

    private String server = "localhost";
    private int port = 8200;

    @RequestMapping(value = "/cloud_api/platforms", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> addPlatform(@RequestBody String body, HttpMethod method, HttpServletRequest request) throws URISyntaxException {
        URI uri = new URI("http", null, server, port, request.getRequestURI(), request.getQueryString(), null);

        System.out.println(uri);
        System.out.println("method: " + method);
        System.out.println("body: " + body);

        String response = RPCMessager.sendMessage(body, RegistrationObjectType.PLATFORM, null);
        return new ResponseEntity<String>(response, HttpStatus.ACCEPTED);
    }
}