package com.umadev.employee.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.umadev.employee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// To work with a JUnit 5 test, we have to extend the test with the RestDocumentationExtension class
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
// Generate documentation
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    List<Employee> employees =null;
    Employee employeeToAdd = null;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation)).build();

        employees=Stream.of(new Employee(1,"Emilio", "Garcia", "emilio@example.com", 1)
                            ,new Employee(2,"Mari", "Rodriguez", "mari@example.com", 1)
                            ,new Employee(3,"Marcelo", "Martinez", "marcelo@example.com", 2)
                            ,new Employee(4,"Adrian", "Martinez", "adrian@example.com", 3)
                            ,new Employee(5,"Juan", "Lopez", "juan@example.com", 5)
                )
                .collect(Collectors.toList());

        // id = 0, because our controller is defined return the same object that we passed  (id=0 in order to create the new employee)
        employeeToAdd = new Employee(0,"Penelope", "Hartmann", "Andreane17@yahoo.com", 5);
    }


    @Test
    public void testGetEmployees() throws Exception {
        this.mockMvc.perform(get("/api/employees")
                        .contentType("application/json"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(employees)))
                .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
                ));
    }

    @Test
    public void testAddEmployee() throws Exception {
        String employeeJson = new ObjectMapper().writeValueAsString(employeeToAdd);
        this.mockMvc.perform(post("/api/employees")
                        .content(employeeJson)
                        .contentType("application/json")).andDo(print())
                        .andExpect(status().isCreated() )
                        .andExpect(MockMvcResultMatchers.content().json(employeeJson))
                        .andDo(document("{methodName}", preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())));
    }

}