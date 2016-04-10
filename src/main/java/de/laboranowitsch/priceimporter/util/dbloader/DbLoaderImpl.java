package de.laboranowitsch.priceimporter.util.dbloader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Implements the {@link DbLoader} interface.
 *
 * Created by cla on 4/10/16.
 */
@Component
public class DbLoaderImpl implements DbLoader {

    private static final Logger LOG = LoggerFactory.getLogger(DbLoaderImpl.class);
    private final String createScriptName;
    private final DataSource dataSource;

    @Autowired
    public DbLoaderImpl(@Value("${priceimporter.create.script}") final String createScriptName, final DataSource dataSource) {
        this.createScriptName = createScriptName;
        this.dataSource = dataSource;
    }

    @Override
    public void prepareDatabase() {
        ClassPathResource resource = new ClassPathResource(createScriptName);
        LOG.info("Executing Loader-Script: {}", createScriptName);
        Connection con = DataSourceUtils.getConnection(dataSource);
        ScriptUtils.executeSqlScript(con, new EncodedResource(resource, "UTF-8"));
        DataSourceUtils.releaseConnection(con, dataSource);
    }
}
