package com.coti.assignment.solution.controller;

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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DirtiesContext
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SolutionControllerExampleTest
{
    @Autowired
    private MockMvc mvc;

    @Test
    public void givenNumberedArray1_whenInsertNumber1_thenStatus200() throws Exception
    {
        this.mvc.perform(get("/insert/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sum of numbers that were logged to the database: 4")));
    }

    @Test
    public void givenNumberedArray1_whenInsertNumber2_thenStatus200() throws Exception
    {
        this.mvc.perform(get("/insert/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sum of numbers that were logged to the database: 9")));
    }

    @Test
    public void givenNumberedArray1_whenInsertNumber3_thenStatus200() throws Exception
    {
        this.mvc.perform(get("/insert/123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Sum of numbers that were logged to the database: 132")));
    }

    @Test
    public void givenNumberedArray2_whenDeleteIndex_thenStatus200() throws Exception
    {
        this.mvc.perform(delete("/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("The index 1 was removed successfully.")));
    }


    @Test
    public void givenNumberedArray3_whenReturnIndex_thenListWithValues() throws Exception
    {
        this.mvc.perform(get("/return/0,1,2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("[4,-1,123]")));
    }

}
