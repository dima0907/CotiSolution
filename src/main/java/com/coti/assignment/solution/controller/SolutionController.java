package com.coti.assignment.solution.controller;

import com.coti.assignment.solution.service.SolutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Scope("prototype")
public class SolutionController
{
    private static final Logger LOGGER = LoggerFactory.getLogger("Solution.logger");

    @Autowired
    private SolutionService solutionService;

    @GetMapping("/insert/{value}")
    public ResponseEntity<String> insertNumber(@PathVariable(name = "value") String numValue)
    {
        return solutionService.insertNumber(numValue);
    }

    @DeleteMapping("/delete/{index}")
    public ResponseEntity<String> removeIndex(@PathVariable(name = "index") String dbIndex)
    {
        return solutionService.removeIndex(dbIndex);
    }

    @GetMapping("/return/{indexes}")
    public ResponseEntity<List<Long>> returnIndexes(@PathVariable(name = "indexes") String[] numIndexes)
    {
        return solutionService.returnIndexes(numIndexes);
    }
}
