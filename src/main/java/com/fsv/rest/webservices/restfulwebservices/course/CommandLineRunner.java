package com.fsv.rest.webservices.restfulwebservices.course;

import com.fsv.rest.webservices.restfulwebservices.course.jpa.springdata.SpringDataJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner {

    /*@Autowired
    private RepositoryJdbc repository;*/

    /*@Autowired
    private JpaRepository repository;*/

    @Autowired
    private SpringDataJpaRepository repository;

    @Override
    public void run(String... args) throws Exception {

        Long courseCount = 0L;

        repository.save(new Course(++courseCount, "Learn JAVA", "Cinthya Rivera Rangel"));
        repository.save(new Course(++courseCount, "Learn Spring Boot", "Cinthya Rivera R"));
        repository.save(new Course(++courseCount, "Learn JPA", "Cinthya Rivera"));
        repository.save(new Course(++courseCount, "Learn JDBC", "Cinthya R"));

        //repository.insert(new Course(2L, "Learn JAVA", "Cinthya Rivera"));
        //Works for JDBC and JPA Repository
        /* repository.insert(new Course(++courseCount, "Learn JAVA", "Cinthya Rivera"));
        repository.insert(new Course(++courseCount, "Learn Spring Boot", "Cinthya Rivera"));
        repository.insert(new Course(++courseCount, "Learn JPA", "Cinthya Rivera"));
        repository.insert(new Course(++courseCount, "Learn JDBC", "Cinthya Rivera"));*/

        repository.deleteById(1L);

        for (long i = 1; i <= courseCount; i++) {
            System.out.println(repository.findById(i));
        }

        System.out.println("All Courses: " + repository.findAll());
        System.out.println("Course Count: " + repository.count());

        System.out.println(repository.findByAuthor("Cinthya Rivera"));
        System.out.println(repository.findByAuthor(""));

        System.out.println(repository.findByName("Learn JAVA"));
        System.out.println(repository.findByName("Learn JPA"));

        System.out.println(repository.findByAuthorAndName("Cinthya Rivera", "Learn JAVA"));
    }
}
