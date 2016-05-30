package de.laboranowitsch.priceimporter.reader;

import de.laboranowitsch.priceimporter.PriceImporterApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Testing of {@link ItemReaderResourceLoader}.
 *
 * @author christian@laboranowitsch.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
@TestPropertySource(properties = {"priceimporter.batch.uploaddirectory=src/test/resources"})
public class ItemReaderResourceLoaderTests {

    @Autowired
    private ItemReaderResourceLoader itemReaderResourceLoader;

	@Test
	public void contextLoads() {
        assertThat("ItemReaderResourceLoader is wired properly", itemReaderResourceLoader, is(notNullValue()));
	}
    @Test
    public void testLoadResourceFromClasspath() {
        Resource resource = itemReaderResourceLoader.getResourceFromClasspath("classpath:GRAPH_30NSW1.csv");
        assertThat("File name matches GRAPH_30NSW1.csv", resource.getFilename(), is(equalTo("GRAPH_30NSW1.csv")));
    }
    @Test
    public void testNonExistingResourceFromClasspath() {
        Resource resource = itemReaderResourceLoader.getResourceFromClasspath("classpath:does_not_exist");
        assertThat("File name does not exist", resource.exists(), is(equalTo(false)));
    }
    @Test
    public void testLoadResourceFromFilepath() throws IOException {
        Resource resource = itemReaderResourceLoader.getResourceFromClasspath("file:" + itemReaderResourceLoader.getUploadDirectory() + "/" + "GRAPH_30NSW1.csv");
        assertThat("File name does not exist", resource.exists(), is(equalTo(true)));
        assertThat("File name matches GRAPH_30NSW1.csv", resource.getFilename(), is(equalTo("GRAPH_30NSW1.csv")));
        assertThat("File name matches full file path",
                resource.getURL().toString(),
                is(equalTo("file:src/test/resources/GRAPH_30NSW1.csv")));
    }
    @Test
    public void testNonExistingResourceFromFilepath() {
        Resource resource = itemReaderResourceLoader.getResourceFromClasspath("file:" + itemReaderResourceLoader.getUploadDirectory() + "/" + "does_not_exist");
        assertThat("File name does not exist", resource.exists(), is(equalTo(false)));
    }
}
