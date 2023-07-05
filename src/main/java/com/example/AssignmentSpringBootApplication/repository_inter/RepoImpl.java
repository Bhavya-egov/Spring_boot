package com.example.AssignmentSpringBootApplication.repository_inter;


import com.example.AssignmentSpringBootApplication.Address;
import com.example.AssignmentSpringBootApplication.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
public class RepoImpl implements Repo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private static final String apiUrl= "https://random-data-api.com/api/v2/users?size=1";


    @PostConstruct
//    public void createTableIfNotExists() {
//        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS userClass (id INT PRIMARY KEY, name VARCHAR(255), gender VARCHAR(10), phone_no VARCHAR(20), address VARCHAR(255))");
//    }

    public void createTable(){

        jdbcTemplate.execute(
                "CREATE TABLE IF NOT EXISTS userClass (id UUID, name VARCHAR(255), gender VARCHAR(255), phone_no VARCHAR(255), address JSON, active BOOLEAN,createdTime BIGINT, PRIMARY KEY (id, active)) PARTITION BY LIST (active);");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS active_user PARTITION OF userClass FOR VALUES IN (TRUE);");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS inactive_user PARTITION OF userClass FOR VALUES IN (FALSE);");
    }

    public int create(User user) {
        String addressJson;
        try {
            String jsonString = this.createUsersFromAPI();
            JsonNode rootNode =  objectMapper.readTree(jsonString);
            JsonNode addressNode = rootNode.get("address");
            Address address = objectMapper.treeToValue(addressNode, Address.class);
            user.setAddress(address);
            addressJson = objectMapper.writeValueAsString(user.getAddress());
            System.out.println(address);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        if (isDuplicateUser(user.getName(), user.getPhone_no())) {
            throw new IllegalArgumentException("User with same name and phone no. already exists.");
        }
        return jdbcTemplate.update("INSERT into userClass(id,name,gender,phone_no,address,active,createdTime) VALUES(?,?,?,?,?::json,?,?)",
                user.getId(), user.getName(), user.getGender(), user.getPhone_no(), addressJson, user.isActive(), user.getCreatedTime());

    }

    private boolean isDuplicateUser(String name, String phone_no) {
        Integer count=jdbcTemplate.queryForObject("SELECT COUNT(*) FROM userClass WHERE name=? and phone_no=?", Integer.class,name,phone_no);
        return count != null && count>0;
    }

    @Override
    public int update(User user) {
        return jdbcTemplate.update("UPDATE userClass SET name=?,gender=?,phone_no=?,address=? WHERE id::text=?",
                new Object[]{user.getId(), user.getName(), user.getGender(), user.getPhone_no(), user.getAddress(), user.isActive()});

    }

    @Override
    public User findById(UUID id) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM userClass WHERE id=?",
                    new CustomUserRowMapper(), id);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }

    public User findByBool(boolean active) {
        try {
            User user = jdbcTemplate.queryForObject("SELECT * FROM userClass where active=?",
                    new CustomUserRowMapper(), active);
            return user;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }

    public User find(UUID id, String phone_no) {
        try {
            if (id != null) {
                User user = jdbcTemplate.queryForObject("SELECT * FROM userClass where id=?",
                        new CustomUserRowMapper(), id);
                return user;
            } else {
                User user = jdbcTemplate.queryForObject("SELECT * FROM userClass where phone_no=?",
                        new CustomUserRowMapper(), phone_no);
                return user;
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }

    }

    @Override
    public int deleteByID(UUID id) {
        return jdbcTemplate.update("DELETE from userClass WHERE id=?", id);
    }



//    RestTemplate restTemplate=new RestTemplate();
//    String apiURL="https://random-data-api.com/api/v2/users?size=1";
//    User[] users=restTemplate.getForObject(apiURL, User[].class);
//    for(User user : users){
//        Address address=new Address();
//        address.setStreet(user.getAddress().getStreet());
//        address.setCity(user.getAddress().getCity());
//        address.setState(user.getAddress().getState());
//        address.setPostalCode(user.getAddress().getPostalCode());
//        User newUser= new User(user.getName(), user.getGender(), user.getPhone_no(), address,user.isActive());
//        Repo.create(newUser);
//    }

    private class CustomUserRowMapper implements org.springframework.jdbc.core.RowMapper<User> {
        public User mapRow(ResultSet rs, int rowNm) throws SQLException {
            User user = new User();
            user.setId(UUID.fromString(rs.getString("id")));
            user.setName(rs.getString("name"));
            user.setGender(rs.getString("gender"));
            user.setPhone_no(rs.getString("phone_no"));
            //user.setAddress(rs.getObject("address", Address.class)); {when address is not null}
            String addressJson = rs.getString("address");
            if (addressJson != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Address address = null  ;
                try {
                    address = objectMapper.readValue(addressJson, Address.class);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                user.setAddress(address);
            }
            user.setActive(rs.getBoolean("active"));
            return user;
        }
    }
//    @PostConstruct
    public String createUsersFromAPI() {
        ResponseEntity<String> response = restTemplate.getForEntity(apiUrl, String.class);
        String responseBody = response.getBody();
        //System.out.println(responseBody);
        return  responseBody;
    }
}
