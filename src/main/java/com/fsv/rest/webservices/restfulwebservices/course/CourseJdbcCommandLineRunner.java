package com.fsv.rest.webservices.restfulwebservices.course;

import com.fsv.rest.webservices.restfulwebservices.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CourseJdbcCommandLineRunner implements CommandLineRunner {

    @Autowired
    private CourseJdbcRepository repository;

    @Override
    public void run(String... args) throws Exception {

        Long courseCount = 1L;

        //repository.insert(new Course(2L, "Learn JAVA", "Cinthya Rivera"));
        repository.insert(new Course(++courseCount, "Learn JAVA2", "Cinthya Rivera"));
        repository.insert(new Course(++courseCount, "Learn JAVA3", "Cinthya Rivera"));

        repository.deleteById(1L);

        System.out.println(repository.findById(2L));
        System.out.println(repository.findById(3L));
    }
}
