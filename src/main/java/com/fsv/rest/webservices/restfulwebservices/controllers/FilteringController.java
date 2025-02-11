package com.fsv.rest.webservices.restfulwebservices.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fsv.rest.webservices.restfulwebservices.models.SomeBean;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FilteringController {

    @GetMapping("/filtering")
    public MappingJacksonValue filtering() {

        SomeBean someBean = new SomeBean("value1", "value2", "value3");

        return getMappingJacksonValue(someBean, "value1", "value3");
    }

    /*
    //Static filtering
    @GetMapping("/filtering"){
        public SomeBean filtering() {
            return new SomeBean
    }*/

    @GetMapping("/filtering-list")
    public MappingJacksonValue filteringList() {

        List<SomeBean> list = Arrays.asList(new SomeBean("value1", "value2", "value3"), new SomeBean("a", "b", "c"), new SomeBean("d", "f", "e"));

        return getMappingJacksonValue(list, "value2", "value3");
    }

    /*
    //Static filtering
    @GetMapping("/filtering-list")
    public List<SomeBean> filteringList() {
        return Arrays.asList(new SomeBean("value1", "value2", "value3"),
                new SomeBean("a", "b", "c"),
                new SomeBean("d", "f", "e"));
    }*/

    private MappingJacksonValue getMappingJacksonValue(Object value, String... properties) {

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(value);

        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept(properties);

        FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);

        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }


}
