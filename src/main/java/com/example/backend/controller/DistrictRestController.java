package com.example.backend.controller;

import com.example.backend.DTO.DistrictDTO;
import com.example.backend.DTO.DistrictResponse;
import com.example.backend.POJO.District;
import com.example.backend.service.DistrictService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class DistrictRestController {

    private DistrictService districtService;

    public DistrictRestController(DistrictService theDistrictService) {
        districtService = theDistrictService;
    }

    @GetMapping("/districts")
    public DistrictResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize
    ) {
        return districtService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/districts/{districtId}")
    public DistrictDTO findById(@PathVariable int districtId) {
        return districtService.findById(districtId);
    }

    @PostMapping("/districts")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody District theDistrict) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theDistrict.setDistrictId(0);
        return districtService.save(theDistrict);
    }

    @PutMapping("/districts")
    public String update(@Valid @RequestBody District theDistrict) {
        return districtService.save(theDistrict);
    }

    @DeleteMapping("/districts/{districtId}")
    public String delete(@PathVariable int districtId) {
        return districtService.delete(districtId);
    }

}
