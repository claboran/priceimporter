package de.laboranowitsch.priceimporter.reader;

import org.springframework.core.io.Resource;

/**
 * Component for loading ItemReader resources via Springs {@link org.springframework.core.io.ResourceLoader}.
 *
 * Created by cla on 5/26/16.
 */
public interface ItemReaderResourceLoader {

    /**
     * Retrieves a resource from classpath
     *
     * @param fileName
     * @return {@link Resource}
     */
    Resource getResourceFromClasspath(String fileName);


    /**
     * Gets the upload directory path
     *
     * @return upload directory
     */
    String getUploadDirectory();
}
