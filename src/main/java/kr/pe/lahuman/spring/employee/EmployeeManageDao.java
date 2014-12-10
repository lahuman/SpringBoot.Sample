package kr.pe.lahuman.spring.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

/**
 * Created by lahuman on 2014-11-26.
 */
@Repository
public class EmployeeManageDao {

    private Logger log = LoggerFactory.getLogger(EmployeeManageDao.class);
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private final String INSERT_SQL = "INSERT INTO WORKER (AGE, NAME, POSITION, SALARY, SEX, REGISTER_DATE) VALUES(:age,:name,:position,:salary,:sex, NOW())";
    private final String UPDATE_SQL = "UPDATE WORKER SET AGE=:age, NAME=:name, POSITION=:position, SALARY=:salary, SEX=:sex, MODIFY_DATE=now() WHERE ID=:id";
    private final String DELETE_SQL = "DELETE FROM WORKER WHERE ID=:id";
    private final String SELECT_SQL = "SELECT ID, AGE, NAME, POSITION, SALARY, SEX, REGISTER_DATE FROM WORKER WHERE ID=:id";
    private final String SELECT_LIST_SQL = "SELECT ID, AGE, NAME, POSITION, SALARY, SEX, REGISTER_DATE FROM WORKER";
    public void insert(EmployeeBean employeeBean){

        jdbcTemplate.update(INSERT_SQL, new BeanPropertySqlParameterSource(employeeBean));

    }

    public void update(EmployeeBean employeeBean){
        jdbcTemplate.update(UPDATE_SQL, new BeanPropertySqlParameterSource(employeeBean));
    }

    public void delete(int id){
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        jdbcTemplate.update(DELETE_SQL, namedParameters);
    }


    public EmployeeBean select(int id){
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(SELECT_SQL, namedParameters, new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));

    }

    public List<EmployeeBean> selectList(EmployeeBean employeeBean){
        return jdbcTemplate.query(SELECT_LIST_SQL, new BeanPropertyRowMapper<EmployeeBean>(EmployeeBean.class));
    }


}
