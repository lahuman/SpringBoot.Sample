package kr.pe.lahuman.spring.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lahuman on 2014-10-14.
 */
@Controller
public class HelloController {
    static final Logger log = LoggerFactory.getLogger(HelloController.class);
    @RequestMapping("/")
    public @ResponseBody String index() {
        log.trace("This is TRACE Log!");
        log.debug("This is DEBUG Log!");
        log.info("This is INFO Log!");
        log.warn("This is WARN Log!");
        log.error("This is ERROR Log!");
        return "Greeting from Sping Boot";
    }

    @RequestMapping("/test.do")
         public String helloWorld(){
        return "test";
    }
}
