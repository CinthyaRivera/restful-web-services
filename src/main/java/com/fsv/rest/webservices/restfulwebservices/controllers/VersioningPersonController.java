package com.fsv.rest.webservices.restfulwebservices.controllers;

import com.fsv.rest.webservices.restfulwebservices.models.Name;
import com.fsv.rest.webservices.restfulwebservices.models.PersonV1;
import com.fsv.rest.webservices.restfulwebservices.models.PersonV2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersioningPersonController {
    @GetMapping("v1/person")
    public PersonV1 getFirstVersionOfPerson() {
        return new PersonV1("Cinthya Rivera");
    }

    @GetMapping("v2/person")
    public PersonV2 getSecondVersionOfPerson() {
        return new PersonV2(new Name("Cinthya", "Rivera"));
    }

    @GetMapping(path = "/person", params = "version=    1")
    public PersonV1 getFirstVersionOfPersonRequestParameter() {
        return new PersonV1("Cinthya Rivera");
    }

    @GetMapping(path = "/person", params = "version=2")
    public PersonV2 getSecondVersionOfPersonRequestParameter() {
        return new PersonV2(new Name("Cinthya", "Rivera"));
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=1")
    public PersonV1 getFirstVersionOfPersonRequestHeader() {
        return new PersonV1("Cinthya Rivera");
    }

    @GetMapping(path = "/person/header", headers = "X-API-VERSION=2")
    public PersonV2 getSecondVersionOfPersonRequestHeader() {
        return new PersonV2(new Name("Cinthya", "Rivera"));
    }

    @GetMapping(path = "/person/accept", produces = "application/app-v1+json")
    public PersonV1 getFirstVersionOfPersonAcceptHeader() {
        return new PersonV1("Cinthya Rivera");
    }

    @GetMapping(path = "/person/accept", produces = "application/app-v2+json")
    public PersonV2 getSecondVersionOfPersonAcceptHeader() {
        return new PersonV2(new Name("Cinthya", "Rivera"));
    }

}
