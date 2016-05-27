package de.laboranowitsch.priceimporter.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.laboranowitsch.priceimporter.PriceImporterApplication;
import de.laboranowitsch.priceimporter.launcher.DemandImportJobLauncher;
import de.laboranowitsch.priceimporter.reader.ItemReaderResourceLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Integration test for {@link JobLauncherRestController}
 *
 * Created by cla on 5/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
@WebAppConfiguration
@TestPropertySource(properties = {"priceimporter.batch.uploaddirectory=src/test/resources/testinput"})
public class JobLauncherRestControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherRestControllerTest.class);


    private MockMvc mockMvc;

    @Autowired
    private ItemReaderResourceLoader itemReaderResourceLoader;

    @Mock
    private DemandImportJobLauncher demandImportJobLauncherMock;

    @InjectMocks
    private JobLauncherRestController jobLauncherRestController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        jobLauncherRestController.setItemReaderResourceLoader(itemReaderResourceLoader);
        mockMvc = MockMvcBuilders.standaloneSetup(jobLauncherRestController).build();
    }

    @Test
    public void testFileNamesDoNotExist() throws Exception {
        mockMvc.perform(post("/api/launch")
                .content(jsonContentAsString(Arrays.asList("is_not_present-1", "is_not_present-2")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verifyZeroInteractions(demandImportJobLauncherMock);
    }

    @Test
    public void testFileNamesDoExist() throws Exception {
        mockMvc.perform(post("/api/launch")
                .content(jsonContentAsString(Arrays.asList("file-does-exist-1.csv")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(demandImportJobLauncherMock).launchDemandImportJob("file:src/test/resources/testinput/file-does-exist-1.csv");
    }

    @Test
    public void testFileNamesExistAndNonExist() throws Exception {
        mockMvc.perform(post("/api/launch")
                .content(jsonContentAsString(Arrays.asList("file-does-exist-1.csv", "file-does-exist-1.csv", "is_not_present-1", "is_not_present-2")))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(demandImportJobLauncherMock, times(2)).launchDemandImportJob(anyString());
    }

    private String jsonContentAsString(List<String> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(files);
        LOGGER.info("Json array: {}", json);
        return json;
    }
}
