package kr.pe.lahuman.spring.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by lahuman on 2014-10-14.
 */
@RestController
public class HelloController {
    @RequestMapping("/")
    public String index() {
        return "Greeting from Sping Boot";
    }
}
