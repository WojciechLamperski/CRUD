package com.example.backend.rest;

import com.example.backend.POJO.Year;
import com.example.backend.service.YearService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class YearRestController {

    private YearService yearService;

    Validator validator = new Validator();

    public YearRestController(YearService theYearService) {
        yearService = theYearService;
    }

    @GetMapping("/years")
    public List<Year> findAll() {
        return yearService.findAll();
    }

    @GetMapping("/years/{yearId}")
    public ResponseEntity<Year> findById(@Valid @RequestBody @PathVariable  int yearId) {
        validator.validateId(yearId);

        return ResponseEntity.ok(yearService.findById(yearId));
    }


    @PostMapping("/years")
    public int save(@Valid @RequestBody Year theYear) {
        validator.validateObject(theYear);
        validator.validateYear(theYear.getYearId());

        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theYear.setYearId(0);
        return yearService.save(theYear);
    }

    @PutMapping("/years")
    public int updateYear(@Valid @RequestBody Year theYear) {
        validator.validateObject(theYear);
        validator.validateYear(theYear.getYear());
        validator.validateId(theYear.getYearId());

        return yearService.update(theYear);
    }

    @DeleteMapping("/years/{yearId}")
    public int deleteYear(@Valid @RequestBody @PathVariable int yearId) {
        validator.validateId(yearId);

        return yearService.delete(yearId);
    }

}

