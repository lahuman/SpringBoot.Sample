package kr.pe.lahuman.spring.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lahuman on 2014-11-27.
 */
@Service
public class EmployeeManageService {
    private Logger log = LoggerFactory.getLogger(EmployeeManageService.class);

    @Autowired
    private EmployeeManageDao employeeManageDao;

    public void employment(EmployeeBean employeeBean){
        employeeManageDao.insert(employeeBean);
    }

    public void changeInfo(EmployeeBean employeeBean){
        employeeManageDao.update(employeeBean);
    }

    public void dismissal(int id){
        employeeManageDao.delete(id);
    }

    public EmployeeBean getInfo(int id){
        return employeeManageDao.select(id);
    }

    public List<EmployeeBean> list(EmployeeBean employeeBean){
        return employeeManageDao.selectList(employeeBean);
    }
}
