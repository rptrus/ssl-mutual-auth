package com.alphastar.SSLServer.rest;

import com.alphastar.SSLServer.json.Response;
import com.alphastar.SSLServer.json.ResponseContainer;
import com.alphastar.SSLServer.model.Person;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class Controller {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping(value = "/gold/person", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseContainer> personMethod(@RequestBody Person person) {
        logger.info("Incoming info (displayed and discarded)");
        logger.info("Name {}", person.getName());
        logger.info("AGE {}", person.getAge());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("Hit the service at {}", format.format(new java.util.Date()));
        ResponseContainer responseContainer = new ResponseContainer();

        //List<Response> response = Collections.singletonList(new Response("Image1","Beachball","The Title"));
        List<Response> responseList = new ArrayList<>();
        Response response1 = new Response();
        Response response2 = new Response();
        response1.setDesc("Beachball");
        response1.setImage("ImageBeachBall.jpg");
        response1.setTitle("Title 1");
        response2.setDesc("Basketball");
        response2.setImage("ImageBasketball.jpg");
        response2.setTitle("Title 2");
        responseList.add(response1);
        responseList.add(response2);
        responseContainer.setResponse(responseList);
        return new ResponseEntity<>(responseContainer, HttpStatus.OK);
    }

    @PostMapping(value = "/bronze/person", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseContainer> personMethodTwo(@RequestBody Person person) {
        preamble(person);
        ResponseContainer responseContainer = new ResponseContainer();

        List<Response> response = Collections.singletonList(new Response("Image2", "Basketball", "The Title2"));
        responseContainer.setResponse(response);
        return new ResponseEntity<>(responseContainer, HttpStatus.OK);

    }

    @PostMapping(value = "/silver/person", produces = "application/json")
    @ResponseBody
    public ResponseEntity<ResponseContainer> personMethodThree(@RequestBody Person person) {
        preamble(person);
        ResponseContainer responseContainer = new ResponseContainer();

        List<Response> response = Collections.singletonList(new Response("SoccerBall.jpg", "Soccer ball", "The Title3"));
        responseContainer.setResponse(response);
        return new ResponseEntity<>(responseContainer, HttpStatus.OK);
    }


    public void preamble(Person person) {
        logger.info("Incoming info (displayed and discarded)");
        logger.info("Name {}", person.getName());
        logger.info(" Age {}", person.getAge());

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("Hit the service at {}", format.format(new java.util.Date()));
    }


}