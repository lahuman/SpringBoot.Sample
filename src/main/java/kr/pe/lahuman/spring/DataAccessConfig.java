package kr.pe.lahuman.spring;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.h2.jdbcx.JdbcDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;


/**
 * Created by lahuman on 2014-11-26.
 */
@Configurable
@EnableTransactionManagement
public class DataAccessConfig implements TransactionManagementConfigurer {

    private Logger log = LoggerFactory.getLogger(DataAccessConfig.class);

    @Autowired
    private Environment env;

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        try {
            return new DataSourceTransactionManager(dataSource());
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource  dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(env.getProperty("db.driver"));
        dataSource.setJdbcUrl(env.getProperty("db.url"));
        dataSource.setUser(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setPreferredTestQuery("select 1");
        dataSource.setMinPoolSize(3);
        dataSource.setMaxPoolSize(20);
        dataSource.setIdleConnectionTestPeriod(300);
        dataSource.setMaxIdleTimeExcessConnections(240);
        dataSource.setAcquireIncrement(1);

        return dataSource;
    }
}
