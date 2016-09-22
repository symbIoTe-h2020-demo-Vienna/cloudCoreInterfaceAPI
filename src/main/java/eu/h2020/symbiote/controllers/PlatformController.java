package eu.h2020.symbiote.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;

/**
 * Created by mateuszl on 22.09.2016.
 */
@CrossOrigin
@RestController
public class PlatformController {

    @RequestMapping(value="/platform", method= RequestMethod.POST)
    public @ResponseBody
    HttpEntity<BigInteger> addPlatform(@RequestBody String platform) {
        RestTemplate restTemplate = new RestTemplate();
        BigInteger id = restTemplate.getForObject("http://localhost:8200/platform", BigInteger.class, platform);

        return new ResponseEntity<BigInteger>(id, HttpStatus.OK);
    }
}
