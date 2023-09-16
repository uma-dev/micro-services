package com.umadev.department.controller;

import capital.scalable.restdocs.AutoDocumentation;
import capital.scalable.restdocs.jackson.JacksonResultHandlers;
import capital.scalable.restdocs.response.ResponseModifyingPreprocessors;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.umadev.department.entity.Department;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// To work with a JUnit 5 test, we have to extend the test with the RestDocumentationExtension class
@ExtendWith({ RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
// Generate documentation
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
// Execute tests based on method name class
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DepartmentControllerTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    List<Department> departments =null;
    Department departmentToAdd1 = null;
    Department departmentToAdd2 = null;

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

        // id = 0, because our controller is defined return the same object that we passed  (id=0 in order to create the new department)
        // id = 1 or 2 will work since controller handle id different to zero
        departmentToAdd1 = new Department(1,"Marketing", "marketing@mail.com");
        departmentToAdd2 = new Department(2, "Sales", "sales@example.com");

        departments=Stream.of(departmentToAdd1, departmentToAdd2)
                          .collect(Collectors.toList());
    }

    @Test
    @Order(1)
    public void testAddDepartment1() throws Exception {
        String departmentJson = new ObjectMapper().writeValueAsString(departmentToAdd1);
        this.mockMvc.perform(post("/api/departments")
                        .content(departmentJson)
                        .contentType("application/json"))
                .andExpect(status().isCreated() )
                // Avoid ID verification since 
                .andExpect( jsonPath("$.name").value(departmentToAdd1.getName()) )
                .andExpect( jsonPath("$.email").value(departmentToAdd1.getEmail()) );
                //.andExpect(MockMvcResultMatchers.content().json(departmentJson));
    }

    @Test
    @Order(2)
    public void testAddDepartment2() throws Exception {
        String departmentJson = new ObjectMapper().writeValueAsString(departmentToAdd2);
        this.mockMvc.perform(post("/api/departments")
                        .content(departmentJson)
                        .contentType("application/json"))
                .andExpect(status().isCreated() )
                // Avoid id verification
                .andExpect( jsonPath("$.name").value(departmentToAdd2.getName()) )
                .andExpect( jsonPath("$.email").value(departmentToAdd2.getEmail()) ); 
                //.andExpect(MockMvcResultMatchers.content().json(departmentJson));
    }
  
    @Test
    @Order(3)
    public void testGetDepartments() throws Exception {
        this.mockMvc.perform(get("/api/departments")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(departments)));
    }

    @Test
    @Order(4)
    public void testUpdateDepartment() throws Exception {
      // Update one field of the Department 2
      departmentToAdd2.setEmail("updatedemail@example.com"); 
      String departmentJson = new ObjectMapper().writeValueAsString(departmentToAdd2);

      this.mockMvc.perform(
                      put("/api/departments")
                      .content(departmentJson)
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                  )
                  .andExpect( status().isOk() )
                  .andExpect( MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(departmentToAdd2)) );
    }

    @Test
    @Order(5)
    public void testDeleteDepartmentOneByIdReturnOk() throws Exception {
      this.mockMvc.perform(
                      delete("/api/departments/{id}", "1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
    }
    
    @Test
    @Order(6)
    public void testDeleteDepartmentTwoByIdReturnOk() throws Exception {
      this.mockMvc.perform(
                      delete("/api/departments/{id}", "2")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                    )
                    .andExpect(status().isOk());
    }

}
