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
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

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
public class JobLauncherRestControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobLauncherRestControllerTest.class);


    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ItemReaderResourceLoader itemReaderResourceLoader;

    @Mock
    private DemandImportJobLauncher demandImportJobLauncherMock;
    @InjectMocks
    private JobLauncherRestController jobLauncherRestController;/* = new JobLauncherRestController();*/

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
//        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
        jobLauncherRestController.setItemReaderResourceLoader(itemReaderResourceLoader);
        mockMvc = MockMvcBuilders.standaloneSetup(jobLauncherRestController).build();
    }

    @Test
    public void testSendList() throws Exception {
        mockMvc.perform(post("/api/launch")
                .content(jsonContentAsString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private String jsonContentAsString() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(Arrays.asList("Hello", "World"));
        LOGGER.info("Json array: {}", json);
        return json;
    }
}
