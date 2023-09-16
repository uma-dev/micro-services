/*-
 * #%L
 * employee
 * %%
 * Copyright (C) 2015 - 2023 Scalable Capital GmbH
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

package com.umadev.employee.controller;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umadev.employee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.ClassOrderer.OrderAnnotation;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.cli.CliDocumentation;
import org.springframework.restdocs.http.HttpDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// To work with a JUnit 5 test, we have to extend the test with the RestDocumentationExtension class
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
// Generate documentation
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
// User order annotation for tests running
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    List<Employee> employees =null;
    Employee employeeToAdd1 = null;
    Employee employeeToAdd2 = null;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext,
                      RestDocumentationContextProvider restDocumentation) {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
                .alwaysDo(MockMvcRestDocumentation.document("{method-name}",
                        Preprocessors.preprocessRequest(),
                        Preprocessors.preprocessResponse(
                                ResponseModifyingPreprocessors.replaceBinaryContent(),
                                ResponseModifyingPreprocessors.limitJsonArrayLength(objectMapper),
                                Preprocessors.prettyPrint())))
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation)
                        .uris()
                        .withScheme("http")
                        .withHost("localhost")
                        .withPort(8080)
                        .and().snippets()
                        .withDefaults(CliDocumentation.curlRequest(),
                                HttpDocumentation.httpRequest(),
                                HttpDocumentation.httpResponse(),
                                AutoDocumentation.requestFields(),
                                AutoDocumentation.responseFields(),
                                AutoDocumentation.pathParameters(),
                                AutoDocumentation.requestParameters(),
                                AutoDocumentation.description(),
                                AutoDocumentation.methodAndPath(),
                                AutoDocumentation.section()))
                .build();

        // id = 0, because our controller is defined return the same object that we passed  (id=0 in order to create the new employee) if not, will set id to ZERO
        // id = 1 and 2, works because of our controller. ASUME EMPTY DB for testing
        employeeToAdd1 = new Employee(1,"Penelope", "Hartmann", "Andreane17@yahoo.com", 1);
        employeeToAdd2 = new Employee(2,"Emilio", "Rodriguez", "emilio@example.com", 2);

        employees=Stream.of(employeeToAdd1,employeeToAdd2)
                        .collect(Collectors.toList());
    }

    @Test
    @Order(1)
    public void testAddEmployee1() throws Exception {
        String employeeJson = new ObjectMapper().writeValueAsString(employeeToAdd1);
        this.mockMvc.perform(post("/api/employees")
                        .content(employeeJson)
                        .contentType("application/json")
                    )
                    .andExpect(status().isCreated() )
                    // Avoid id  verification
    	              .andExpect( jsonPath("$.firstName").value(employeeToAdd1.getFirstName()) )
    	              .andExpect( jsonPath("$.lastName").value(employeeToAdd1.getLastName()) )
    	              .andExpect( jsonPath("$.email").value(employeeToAdd1.getEmail()) )
    	              .andExpect( jsonPath("$.departmentId").value(employeeToAdd1.getDepartmentId()) );
                    // To verify all the Json 
                    //.andExpect(MockMvcResultMatchers.content().json(employeeJson));
    }

    @Test
    @Order(2)
    public void testAddEmployee2() throws Exception {
        String employeeJson = new ObjectMapper().writeValueAsString(employeeToAdd2);
        this.mockMvc.perform(post("/api/employees")
                        .content(employeeJson)
                        .contentType("application/json")
                    )
                    .andExpect(status().isCreated() )
                    // Avoid id  verification
    	              .andExpect( jsonPath("$.firstName").value(employeeToAdd2.getFirstName()) )
    	              .andExpect( jsonPath("$.lastName").value(employeeToAdd2.getLastName()) )
    	              .andExpect( jsonPath("$.email").value(employeeToAdd2.getEmail()) )
    	              .andExpect( jsonPath("$.departmentId").value(employeeToAdd2.getDepartmentId()) );
                    //.andExpect(MockMvcResultMatchers.content().json(employeeJson));
    }
    @Test
    @Order(3)
    public void testGetEmployees() throws Exception {
        this.mockMvc.perform(get("/api/employees")
                        .contentType("application/json")
                      )
                      .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(employees)));
    }

    @Test 
    @Order(4)
    public void testUpdateEmployee() throws Exception {
      // Update one field of the second employee
      employeeToAdd2.setLastName("Gonzalez");
      String employeeJson = new ObjectMapper().writeValueAsString(employeeToAdd2);

      this.mockMvc.perform(MockMvcRequestBuilders
                  .put("/api/employees")
                  .content(employeeJson)
                  .contentType(MediaType.APPLICATION_JSON)
                  .accept(MediaType.APPLICATION_JSON) 
                  )
          .andExpect(status().isOk())
          .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(employeeToAdd2)));
    }

    @Test
    @Order(5)
    public void testDeleteEmployeeOneByIdReturnOk() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                    .delete("/api/employees/{id}", "1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                  )
                  .andExpect(status().isOk());
    }
    
    @Test
    @Order(6)
    public void testDeleteEmployeeTwoByIdReturnOk() throws Exception {
      this.mockMvc.perform(MockMvcRequestBuilders
                    .delete("/api/employees/{id}", "2")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                  )
                  .andExpect(status().isOk());
    }
}
