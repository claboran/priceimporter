package de.laboranowitsch.priceimporter.reader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

/**
 * Implementation class for {@link ItemReaderResourceLoader}.
 *
 * Created by cla on 5/26/16.
 */
@Component
public class ItemReaderResourceLoaderImpl implements ItemReaderResourceLoader, ResourceLoaderAware {

    private ResourceLoader resourceLoader;
    private final String uploadDirectory;

    @Autowired
    public ItemReaderResourceLoaderImpl(@Value("${priceimporter.batch.uploaddirectory}") final String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    @Override
    public Resource getResourceFromClasspath(String fileName) {
        return resourceLoader.getResource(fileName);
    }

    @Override
    public String getUploadDirectory() {
        return uploadDirectory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }
}
