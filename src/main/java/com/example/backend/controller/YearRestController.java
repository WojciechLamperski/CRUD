package com.example.backend.controller;

import com.example.backend.model.YearModel;
import com.example.backend.model.YearRequest;
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
    @ResponseStatus(HttpStatus.OK)
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
    @ResponseStatus(HttpStatus.OK)
    public YearModel findById(@PathVariable int yearId) {
        logger.info("find year by Id incoming request");
        return yearService.findById(yearId);
    }

    @PostMapping("/years")
    @ResponseStatus(HttpStatus.CREATED)
    public YearModel save(@Valid @RequestBody YearRequest theYear) {
        theYear.setYearId(0);
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        logger.info("save year incoming request {}", theYear);
        return yearService.save(theYear);
    }

    @PutMapping("/years")
    @ResponseStatus(HttpStatus.OK)
    public YearModel update(@Valid @RequestBody YearRequest theYear) {
        logger.info("update year incoming request {}", theYear);
        return yearService.save(theYear);
    }

    @DeleteMapping("/years/{yearId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int yearId) {
        logger.info("delete year incoming request");
        yearService.delete(yearId);
    }

}

