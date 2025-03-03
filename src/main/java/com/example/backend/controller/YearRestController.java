package com.example.backend.controller;

import com.example.backend.model.YearResponse;
import com.example.backend.entity.YearEntity;
import com.example.backend.service.YearService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class YearRestController {

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
        return yearService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/years/{yearId}")
    public YearEntity findById(@PathVariable int yearId) {
        return yearService.findById(yearId);
    }

    @PostMapping("/years")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody YearEntity theYear) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theYear.setYearId(0);
        return yearService.save(theYear);
    }

    @PutMapping("/years")
    public String update(@Valid @RequestBody YearEntity theYear) {
        return yearService.save(theYear);
    }

    @DeleteMapping("/years/{yearId}")
    public String delete(@PathVariable int yearId) {
        return yearService.delete(yearId);
    }

}

