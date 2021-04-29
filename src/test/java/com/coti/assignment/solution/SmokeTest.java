package com.coti.assignment.solution;

import static org.assertj.core.api.Assertions.assertThat;

import com.coti.assignment.solution.controller.SolutionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SmokeTest
{
    @Autowired
    private SolutionController controller;

    @Test
    public void contextLoads() throws Exception
    {
        assertThat(controller).isNotNull();
    }
}
