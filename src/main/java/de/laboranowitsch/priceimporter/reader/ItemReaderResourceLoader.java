package de.laboranowitsch.priceimporter.reader;

import org.springframework.core.io.Resource;

/**
 * Component for loading ItemReader resources via Springs {@link org.springframework.core.io.ResourceLoader}.
 *
 * @author christian@laboranowitsch.de
 */
public interface ItemReaderResourceLoader {

    /**
     * Retrieves a resource from classpath or filepath.
     *
     * @param fileName prefixed with classpath: or file:
     * @return {@link Resource}
     */
    Resource getResourceFromClasspath(String fileName);


    /**
     * Gets the configured upload directory.
     *
     * @return upload directory
     */
    String getUploadDirectory();
}
