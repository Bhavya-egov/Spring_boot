package com.example.AssignmentSpringBootApplication.repository_inter;


import com.example.AssignmentSpringBootApplication.UserClass;
import com.example.AssignmentSpringBootApplication.UserSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;

@Repository
public class RepoImpl implements Repo{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void createTableIfNotExists() {
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS userClass (id INT PRIMARY KEY, name VARCHAR(255), gender VARCHAR(10), phone_no VARCHAR(20), address VARCHAR(255))");
    }
    public int create(UserClass userClass){
        return jdbcTemplate.update("INSERT into userClass(id,name,gender,phone_no,address) VALUES(?,?,?,?,?)",
        new Object[] { userClass.getId(), userClass.getName(), userClass.getGender(), userClass.getPhone_no(), userClass.getAddress()});

    }
    @Override
    public int update(UserClass userClass){
        return jdbcTemplate.update("UPDATE userClass SET name=?,gender=?,phone_no=?,address=? WHERE id::text=?",
                new Object[] { userClass.getId(), userClass.getName(), userClass.getGender(), userClass.getPhone_no(), userClass.getAddress()});

    }

    @Override
    public UserClass find_by_id(int id) {
        try{
            UserClass userClass = jdbcTemplate.queryForObject("SELECT * FROM userClass WHERE id=?",
                    BeanPropertyRowMapper.newInstance(UserClass.class),id);
            return userClass;
        } catch(IncorrectResultSizeDataAccessException e){
            return null;
        }

    }
    public UserClass find(int id,String phone_no){
        try{
            if(id != 0){
                UserClass userClass = jdbcTemplate.queryForObject("SELECT * FROM userClass where id=?",
                        BeanPropertyRowMapper.newInstance(UserClass.class),id);
                return userClass;
            }
            else{
                UserClass userClass = jdbcTemplate.queryForObject("SELECT * FROM userClass where phone_no=?",
                        BeanPropertyRowMapper.newInstance(UserClass.class),phone_no);
                return userClass;
            }
        }catch (IncorrectResultSizeDataAccessException e){
            return null;
        }

    }
    @Override
    public int delete_by_id(int id){
        return jdbcTemplate.update("DELETE from userClass WHERE id=?",id);
    }
}
