package de.laboranowitsch.priceimporter;

import de.laboranowitsch.priceimporter.util.dbloader.DbLoader;
import de.laboranowitsch.priceimporter.util.dbloader.DbLoaderImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PriceImporterApplication {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(PriceImporterApplication.class, args);
		DbLoader dbLoader = ctx.getBean(DbLoaderImpl.class);
		dbLoader.prepareDatabase();
	}
}
