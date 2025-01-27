package com.example.backend.controller;

import com.example.backend.POJO.Year;
import com.example.backend.service.YearService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class YearRestController {

    private YearService yearService;

    public YearRestController(YearService theYearService) {
        yearService = theYearService;
    }

    @GetMapping("/years")
    public ResponseEntity<List<Year>> findAll() {
        return ResponseEntity.ok(yearService.findAll());
    }

    @GetMapping("/years/{yearId}")
    public ResponseEntity<Year> findById(@PathVariable int yearId) {
        return ResponseEntity.ok(yearService.findById(yearId));
    }

    @PostMapping("/years")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody Year theYear) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theYear.setYearId(0);
        return yearService.save(theYear);
    }

    @PutMapping("/years")
    public String updateYear(@Valid @RequestBody Year theYear) {
        return yearService.save(theYear);
    }

    @DeleteMapping("/years/{yearId}")
    public String deleteYear(@PathVariable int yearId) {
        return yearService.delete(yearId);
    }

}

