package com.example.backend.controller;

import com.example.backend.model.PopulationModel;
import com.example.backend.model.PopulationResponse;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.service.PopulationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PopulationRestController {

    private final PopulationService populationService;

    public PopulationRestController(PopulationService thePopulationService) {
        populationService = thePopulationService;
    }

    @GetMapping("/populations")
    public PopulationResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
            ) {
        return populationService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/districts/{districtId}/populations")
    public PopulationResponse findAllInDistrict(
            @PathVariable int districtId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        return populationService.findAllInDistrict(districtId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/years/{yearId}/populations")
    public PopulationResponse findAllInYear(
            @PathVariable int yearId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        return populationService.findAllInYear(yearId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/voivodeships/{voivodeshipId}/populations")
    public PopulationResponse findAllInVoivodeship(
            @PathVariable int voivodeshipId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        return populationService.findAllInVoivodeship(voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/populations/{populationId}")
    public PopulationModel findById(@PathVariable int populationId) {
        return populationService.findById(populationId);
    }

    @PostMapping("/populations")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody PopulationEntity thePopulation) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        thePopulation.setPopulationId(0);
        return populationService.save(thePopulation);
    }

    @PutMapping("/populations")
    public String update(@Valid @RequestBody PopulationEntity thePopulation) {
        return populationService.save(thePopulation);
    }

    @DeleteMapping("/populations/{populationId}")
    public String delete(@PathVariable int populationId) {
        return populationService.delete(populationId);
    }

}
