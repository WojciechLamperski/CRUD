package com.example.backend.controller;

import com.example.backend.model.YearResponse;
import com.example.backend.entity.YearEntity;
import com.example.backend.service.YearService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class YearRestController {

    private Logger logger = LoggerFactory.getLogger(YearRestController.class);

    private final YearService yearService;

    public YearRestController(YearService theYearService) {
        yearService = theYearService;
    }

    @GetMapping("/years")
    public YearResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "yearId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all years incoming request");
        return yearService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/years/{yearId}")
    public YearEntity findById(@PathVariable int yearId) {
        logger.info("find year by Id incoming request");
        return yearService.findById(yearId);
    }

    @PostMapping("/years")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody YearEntity theYear) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        logger.info("save year incoming request {}", theYear);
        theYear.setYearId(0);
        return yearService.save(theYear);
    }

    @PutMapping("/years")
    public String update(@Valid @RequestBody YearEntity theYear) {
        logger.info("update year incoming request {}", theYear);
        return yearService.save(theYear);
    }

    @DeleteMapping("/years/{yearId}")
    public String delete(@PathVariable int yearId) {
        logger.info("delete year incoming request");
        return yearService.delete(yearId);
    }

}

