package de.laboranowitsch.priceimporter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * RestController for submitting import files to the launcher.
 *
 * Created by cla on 5/25/16.
 */
@RestController
@RequestMapping("/api")
public class JobLauncherRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherRestController.class);


    @RequestMapping(value = "/launch", method = RequestMethod.POST)
    public ResponseEntity<Void> launchJobs(@RequestBody List<String> files) {
        files.stream().forEach(f -> LOGGER.info("File: {}", f));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
