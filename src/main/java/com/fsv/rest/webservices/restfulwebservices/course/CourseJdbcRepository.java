package com.fsv.rest.webservices.restfulwebservices.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CourseJdbcRepository {

    private static String INSERT_QUERY =
            """
                                INSERT INTO COURSE
                                VALUES (?,?,?);
                    """;

    private static String DELETE_QUERY =
            """
                                DELETE FROM COURSE
                                WHERE ID=?;
                    """;

    private static String SELECT_QUERY =
            """
                                SELECT * FROM COURSE
                                WHERE ID=?;
                    """;

    @Autowired
    private JdbcTemplate springJdbcTemplate;

    public void insert(Course course) {
        springJdbcTemplate.update(INSERT_QUERY, course.getId(), course.getName(), course.getAuthor());
    }

    public void deleteById(Long id) {
        springJdbcTemplate.update(DELETE_QUERY, id);
    }

    public Course findById(Long id) {
        return springJdbcTemplate.queryForObject(SELECT_QUERY, new BeanPropertyRowMapper<>(Course.class), id);
    }

}
