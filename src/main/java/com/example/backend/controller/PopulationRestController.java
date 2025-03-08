package com.example.backend.controller;

import com.example.backend.model.PopulationModel;
import com.example.backend.model.PopulationResponse;
import com.example.backend.entity.PopulationEntity;
import com.example.backend.service.PopulationService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class PopulationRestController {

    private Logger logger = LoggerFactory.getLogger(PopulationRestController.class);

    private final PopulationService populationService;

    public PopulationRestController(PopulationService thePopulationService) {
        populationService = thePopulationService;
    }

    @GetMapping("/populations")
    @ResponseStatus(HttpStatus.OK)
    public PopulationResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all populations incoming request");
        return populationService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/districts/{districtId}/populations")
    @ResponseStatus(HttpStatus.OK)
    public PopulationResponse findAllInDistrict(
            @PathVariable int districtId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all populations in a specified district incoming request");
        return populationService.findAllInDistrict(districtId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/years/{yearId}/populations")
    @ResponseStatus(HttpStatus.OK)
    public PopulationResponse findAllInYear(
            @PathVariable int yearId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all populations in a specified year incoming request");
        return populationService.findAllInYear(yearId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/voivodeships/{voivodeshipId}/populations")
    @ResponseStatus(HttpStatus.OK)
    public PopulationResponse findAllInVoivodeship(
            @PathVariable int voivodeshipId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "populationId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all populations in a specified voivodeship incoming request");
        return populationService.findAllInVoivodeship(voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/populations/{populationId}")
    @ResponseStatus(HttpStatus.OK)
    public PopulationModel findById(@PathVariable int populationId) {
        logger.info("find population by Id incoming request");
        return populationService.findById(populationId);
    }

    @PostMapping("/populations")
    @ResponseStatus(HttpStatus.CREATED)
    public PopulationModel save(@Valid @RequestBody PopulationEntity thePopulation) {
        logger.info("save population incoming request {}", thePopulation);
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        thePopulation.setPopulationId(0);
        return populationService.save(thePopulation);
    }

    @PutMapping("/populations")
    @ResponseStatus(HttpStatus.OK)
    public PopulationModel update(@Valid @RequestBody PopulationEntity thePopulation) {
        logger.info("update population incoming request {}", thePopulation);
        return populationService.save(thePopulation);
    }

    @DeleteMapping("/populations/{populationId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable int populationId) {
        logger.info("delete population incoming request");
        return populationService.delete(populationId);
    }

}
