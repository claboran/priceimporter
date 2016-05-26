package de.laboranowitsch.priceimporter.rest;

import de.laboranowitsch.priceimporter.launcher.DemandImportJobLauncher;
import de.laboranowitsch.priceimporter.reader.ItemReaderResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RestController for submitting import files to the launcher.
 *
 * Created by cla on 5/25/16.
 */
@RestController
@RequestMapping("/api")
public class JobLauncherRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherRestController.class);


    @Autowired
    private ItemReaderResourceLoader itemReaderResourceLoader;
    @Autowired
    private DemandImportJobLauncher demandImportJobLauncher;

    @RequestMapping(value = "/launch", method = RequestMethod.POST)
    public ResponseEntity<Void> launchJobs(@RequestBody List<String> files) {
        //Prepare file names
        List<String> fileNames = files.stream().map(fn -> "file:" + itemReaderResourceLoader.getUploadDirectory() + "/" + fn).collect(Collectors.toList());
        fileNames.stream().forEach(f -> LOGGER.info("Received files for processing: {}", f));
        //Log all non existing files
        getNonExistingFileResources(fileNames).stream().forEach(r -> LOGGER.error("Non existing files: {}", r.getFilename()));
        //process all existing files
        getExistingFileResources(fileNames).stream().forEach(resource -> {
            try {
                executeBatch(resource.getURL().toString());
            } catch (Exception e) {
                LOGGER.error("Error occurred: {}", e.getMessage());
            }
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private List<Resource> getNonExistingFileResources(List<String> files) {
        return files.stream().map(file -> itemReaderResourceLoader.getResourceFromClasspath(file))
                .filter(resource -> !resource.exists()).collect(Collectors.toList());
    }
    private List<Resource> getExistingFileResources(List<String> files) {
        return files.stream().map(file -> itemReaderResourceLoader.getResourceFromClasspath(file))
                .filter(resource -> resource.exists()).collect(Collectors.toList());
    }

    private void executeBatch(String fileName) throws Exception {
        LOGGER.info("Execute Batch for file: {}", fileName);
        demandImportJobLauncher.launchDemandImportJob(fileName);
    }
    public void setItemReaderResourceLoader(ItemReaderResourceLoader itemReaderResourceLoader) {
        this.itemReaderResourceLoader = itemReaderResourceLoader;
    }
}
