package com.example.backend.controller;

import com.example.backend.POJO.Population;
import com.example.backend.service.PopulationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PopulationRestController {

    private PopulationService populationService;

    public PopulationRestController(PopulationService thePopulationService) {
        populationService = thePopulationService;
    }

//    TODO add Pagination, DTO, & un-comment
//    @GetMapping("/populations")
//    public List<Population> findAll() {
//        return populationService.findAll();
//    }

    @GetMapping("/populations/{populationId}")
    public Population findById(@PathVariable int populationId) {
        return populationService.findById(populationId);
    }

    @PostMapping("/populations")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody Population thePopulation) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        thePopulation.setPopulationId(0);
        return populationService.save(thePopulation);
    }

    @PutMapping("/populations")
    public String update(@Valid @RequestBody Population thePopulation) {
        return populationService.save(thePopulation);
    }

    @DeleteMapping("/populations/{populationId}")
    public String delete(@PathVariable int populationId) {
        return populationService.delete(populationId);
    }

}
