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

import static org.springframework.jdbc.datasource.init.ScriptUtils.*;

/**
 * Implements the {@link DbLoader} interface.
 *
 * Created by cla on 4/10/16.
 */
@Component
public class DbLoaderImpl implements DbLoader {

    private static final Logger LOG = LoggerFactory.getLogger(DbLoaderImpl.class);
    private final String createScriptName;
    private final String dropScriptName;
    private final DataSource dataSource;
    private final boolean runScripts;

    @Autowired
    public DbLoaderImpl(@Value("${priceimporter.create.script}") final String createScriptName,
                        @Value("${priceimporter.drop.script}") final String dropScriptName,
                        @Value("${priceimporter.run.script}") final boolean runScripts,
                        final DataSource dataSource) {
        this.createScriptName = createScriptName;
        this.dropScriptName = dropScriptName;
        this.runScripts = runScripts;
        this.dataSource = dataSource;
    }

    @Override
    public void prepareDatabase() {
        if(runScripts) {
            executeDropScript();
            executeCreateScript();
        }
    }

    private void executeDropScript() {
        ClassPathResource resource = new ClassPathResource(dropScriptName);
        LOG.info("Executing DROP-Script: {}", createScriptName);
        Connection con = DataSourceUtils.getConnection(dataSource);
        ScriptUtils.executeSqlScript(con, new EncodedResource(resource, "UTF-8"), true, true, DEFAULT_COMMENT_PREFIX, DEFAULT_STATEMENT_SEPARATOR,
                DEFAULT_BLOCK_COMMENT_START_DELIMITER, DEFAULT_BLOCK_COMMENT_END_DELIMITER);
        DataSourceUtils.releaseConnection(con, dataSource);
    }

    private void executeCreateScript() {
        ClassPathResource resource = new ClassPathResource(createScriptName);
        LOG.info("Executing CREATE-Script: {}", createScriptName);
        Connection con = DataSourceUtils.getConnection(dataSource);
        ScriptUtils.executeSqlScript(con, new EncodedResource(resource, "UTF-8"));
        DataSourceUtils.releaseConnection(con, dataSource);

    }
}
