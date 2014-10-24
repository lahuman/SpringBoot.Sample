package kr.pe.lahuman.spring.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * Created by lahuman on 2014-10-14.
 */
@Controller
public class HelloController {
    static final Logger log = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private LocaleResolver localeResolver;

    @RequestMapping("/")
    public @ResponseBody String index(HttpServletRequest request) {

        String usMessage = messageSource.getMessage("hello.test", null, "no surch", Locale.US);
        String korMessage = messageSource.getMessage("hello.test",null, "no surch", Locale.KOREA);
        String localeMessage = messageSource.getMessage("hello.test",null, "no surch", localeResolver.resolveLocale(request));

        log.debug(korMessage);
        log.debug(usMessage);
        log.debug(localeMessage);

        log.trace("This is TRACE Log!");
        log.debug("This is DEBUG Log!");
        log.info("This is INFO Log!");
        log.warn("This is WARN Log!");
        log.error("This is ERROR Log!");
        return "Greeting from Sping Boot";
    }

    @RequestMapping("/test.do")
    public String helloWorld(HttpServletRequest request, HttpServletResponse response){
        String usMessage = messageSource.getMessage("hello.test", null, "no surch", Locale.US);
        String korMessage = messageSource.getMessage("hello.test",null, "no surch", Locale.KOREA);
        String localeMessage = messageSource.getMessage("hello.test",null, "no surch", localeResolver.resolveLocale(request));

        log.debug("KR: " + korMessage);
        log.debug("US: " + usMessage);
        log.debug("USER SET: " + localeMessage);
        log.debug("response SET: " + response.getLocale().toString());
        return "test";
    }
}
