package kr.pe.lahuman.spring.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

/**
 * Created by lahuman on 2014-12-09.
 */
@Controller
public class EmployeeController {
    static final Logger log = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeManageService employeeManageService;



    @RequestMapping(value = "/api/employees", method = RequestMethod.GET)
    @ResponseBody
    public List<EmployeeBean> employeesList(HttpServletRequest request, HttpServletResponse response){
        log.debug("call emplyeeList");
        List<EmployeeBean> result = employeeManageService.list(null);
        return result;
    }


    @RequestMapping(value = "/api/employees/{id}", method = RequestMethod.GET)
    @ResponseBody
    public EmployeeBean getEmployeeInfo(@PathVariable int id,  HttpServletRequest request, HttpServletResponse response){
        log.debug("call getEmployeeInfo ID : " + id);
        return employeeManageService.getInfo(id);
    }

    @RequestMapping(value = "/api/employees", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void addEmployeeInfo(@RequestBody EmployeeBean employeeBean, HttpServletRequest request, HttpServletResponse response){
        log.debug("call add Employee "+ employeeBean.getName());
        employeeManageService.employment(employeeBean);
    }

    @RequestMapping(value = "/api/employees/{id}", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void updateEmployeeInfo(@RequestBody EmployeeBean employeeBean, HttpServletRequest request, HttpServletResponse response){
        log.debug("call updateEmployee"+ employeeBean.getId());
        employeeManageService.changeInfo(employeeBean);
    }

    @RequestMapping(value = "/api/employees/{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void deleteEmployeeInfo(@PathVariable int id, HttpServletRequest request, HttpServletResponse response){
        log.debug("call deleteEmployee");
        employeeManageService.dismissal(id);
    }
}
