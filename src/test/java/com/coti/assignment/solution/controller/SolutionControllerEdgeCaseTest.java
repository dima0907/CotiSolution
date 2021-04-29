package com.coti.assignment.solution.controller;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.coti.assignment.solution.persistence.domain.NumberedArrayEntity;
import com.coti.assignment.solution.persistence.repository.INumberedArrayEntity;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class SolutionControllerEdgeCaseTest
{
    @Autowired
    private MockMvc mvc;

    @Autowired
    private INumberedArrayEntity repo;

    @Test
    public void givenNumberedArray1_whenInsertNumber_thenStatus200() throws Exception
    {
        this.mvc.perform(get("/insert/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sum of numbers that were logged to the database:")));
    }

    @Test
    public void givenNumberedArray1_whenInsertNegNumber_thenStatus400() throws Exception
    {
        this.mvc.perform(get("/insert/-4"))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(containsString("Not a valid input was passed , please revise and try once again.")));
    }

    @Test
    public void givenNumberedArray2_whenReturnIndexNotValid_thenEmptyList() throws Exception
    {
        this.mvc.perform(get("/return/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    public void givenNumberedArray3_whenDeleteIndex_thenStatus200() throws Exception
    {
        this.mvc.perform(delete("/delete/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The index 0 was removed successfully.")));
    }

    @Test
    public void givenNumberedArray4_whenReturnIndex_thenListWithValues() throws Exception
    {
        this.mvc.perform(get("/return/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[-1]")));
    }

    @Test
    public void givenNumberedArray5_whenInsertNumber_thenStatus200() throws Exception
    {
        this.mvc.perform(get("/insert/1234567890"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sum of numbers that were logged to the database: 1234567894")));
    }

    @Test
    public void givenNumberedArray6_whenDeleteIndex_thenStatus200() throws Exception
    {
        this.mvc.perform(delete("/delete/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The index 0 was removed successfully.")));
    }

    @Test
    public void givenNumberedArray7_whenDeleteIndex_thenDbValuesSwapped()
    {
        List<NumberedArrayEntity> dbArray = repo.findAll();
        Assertions.assertThat(dbArray.toString()).isEqualTo("[{id=1, value=1234567890}, {id=2, value=4}]");
    }


}
