package kr.pe.lahuman.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.h2.Driver;
import org.h2.tools.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Locale;

/**
 * Created by lahuman on 2014-10-14.
 */
@EnableWebMvc
@Configuration
@EnableAutoConfiguration
@ComponentScan
//하나의 properties를 사용할 경우
//@PropertySource("classpath:${APP_ENV:default}.properties")
//여러 properties를 사용할 경우
@PropertySources({
    @PropertySource("classpath:${APP_ENV:DEV}.db.properties"),
    @PropertySource("classpath:${APP_ENV:DEV}.setting.properties")
        //물리 위치에서 파일을 찾을 경우
//    @PropertySource("file:/data/${APP_ENV:DEV}.setting.properties")
})
@Import({DataAccessConfig.class})
public class Application  extends WebMvcConfigurerAdapter  implements InitializingBean, DisposableBean {
    static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private Environment env;

    @Override
    public void configureDefaultServletHandling(
            DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    }

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceEncoding(true);
        return characterEncodingFilter;
    }

    public static void main(String[] args) {
        //Spring Boot 기동
        ApplicationContext ctx = SpringApplication.run(new Class[] {Application.class}, args);
        log.info("Let's inspct the bens provider by Spring Boot:");
//
//        String[] beanNames = ctx.getBeanDefinitionNames();
//        Arrays.sort(beanNames);
//        for (String beanName : beanNames) {
//            log.info(beanName);
//        }



    }


    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        //WEB-INF 밑에 해당 폴더에서 properties를 찾는다.
        messageSource.setBasename("messages/messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor=new LocaleChangeInterceptor();
        //request로 넘어오는 language parameter를 받아서 locale로 설정 한다.
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver sessionLocaleResolver(){
        //세션 기준으로 로케일을 설정 한다.
        SessionLocaleResolver localeResolver=new SessionLocaleResolver();
        //쿠키 기준(세션이 끊겨도 브라우져에 설정된 쿠키 기준으로)
//        CookieLocaleResolver localeResolver = new CookieLocaleResolver();

        //최초 기본 로케일을 강제로 설정이 가능 하다.
        localeResolver.setDefaultLocale(new Locale(env.getProperty("default.locale")));

        return localeResolver;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        //Interceptor를 추가 한다.
        registry.addInterceptor(localeChangeInterceptor());
    }

    Server server = null;
    @Override
    public void destroy() throws Exception {
        log.debug("STOP H2 DATABASE");
        if(server != null) {
            server.stop();
        }

    }



    static {
        try {
            DriverManager.registerDriver(new Driver());
        } catch (Exception e) {}
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("START H2 DATABASE");
        server = Server.createTcpServer( "-tcpPort", "9123").start();


        // now use the database in your application in embedded mode
        Class.forName("org.h2.Driver");
        Connection conn =
                DriverManager.getConnection("jdbc:h2:~/lahuman", "sa", "");

        Statement stat = conn.createStatement();
        stat.execute("CREATE TABLE IF NOT EXISTS WORKER(ID INT auto_increment PRIMARY KEY , AGE INT, NAME VARCHAR(20), POSITION VARCHAR(10), SALARY INT, SEX BOOLEAN, REGISTER_DATE DATE,  MODIFY_DATE DATE)");

        ResultSet rs = stat.executeQuery("SELECT ID FROM WORKER");
        while(rs.next()){
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            log.debug(rs.getString(1));
            log.debug("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        }
        rs.close();
        conn.close();

    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        final ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        converter.setObjectMapper(objectMapper);
        converters.add(converter);
        super.configureMessageConverters(converters);
    }
}
