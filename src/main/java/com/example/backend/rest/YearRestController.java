package com.example.backend.rest;

import com.example.backend.POJO.Year;
import com.example.backend.service.YearService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api")
public class YearRestController {

    // TODO
    // Add input validation ie. year can't be null

    private YearService yearService;

    public YearRestController(YearService theYearService) {
        yearService = theYearService;
    }

    @GetMapping("/years")
    public List<Year> findAll() {
        return yearService.findAll();
    }

    @GetMapping("/years/{yearId}")
    public Year findById(@PathVariable int yearId) {
        return yearService.findById(yearId);
    }

    // implement save
    @PostMapping("/years")
    public int save(@RequestBody Year theYear) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theYear.setYearId(0);
        return yearService.save(theYear);
    }

    // implement update
    @PutMapping("/years")
    public int updateEmployee(@RequestBody Year theYear) {
        return yearService.update(theYear);
    }

    // implement delete
    @PutMapping("/years/{yearId}")
    public int deleteEmployee(@PathVariable int yearId) {
        return yearService.delete(yearId);
    }

}

