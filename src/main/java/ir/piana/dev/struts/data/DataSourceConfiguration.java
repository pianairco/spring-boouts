package ir.piana.dev.struts.data;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * Created by mj.rahmati on 12/24/2019.
 */
@Configuration()
public class DataSourceConfiguration {
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.t2anf04")
    public DataSourceProperties t2anf04DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.t2anf04.configuration")
    public DataSource t2anf04DataSource(@Qualifier("t2anf04DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean
    @ConfigurationProperties("app.datasource.t2anf03")
    public DataSourceProperties t2anf03DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("app.datasource.t2anf03.configuration")
    public DataSource t2anf03DataSource(@Qualifier("t2anf03DataSourceProperties") DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }
}
