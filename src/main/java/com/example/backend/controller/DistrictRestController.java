package com.example.backend.controller;

import com.example.backend.model.DistrictModel;
import com.example.backend.model.DistrictResponse;
import com.example.backend.entity.DistrictEntity;
import com.example.backend.service.DistrictService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class DistrictRestController {

    private final DistrictService districtService;

    public DistrictRestController(DistrictService theDistrictService) {
        districtService = theDistrictService;
    }

    @GetMapping("/districts")
    public DistrictResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "districtId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        return districtService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/voivodeships/{voivodeshipId}/districts")
    public DistrictResponse findAllInVoivodeship(
            @PathVariable int voivodeshipId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "districtId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        return districtService.findAllInVoivodeship(voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/districts/{districtId}")
    public DistrictModel findById(@PathVariable int districtId) {
        return districtService.findById(districtId);
    }

    @PostMapping("/districts")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody DistrictEntity theDistrict) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theDistrict.setDistrictId(0);
        return districtService.save(theDistrict);
    }

    @PutMapping("/districts")
    public String update(@Valid @RequestBody DistrictEntity theDistrict) {
        return districtService.save(theDistrict);
    }

    @DeleteMapping("/districts/{districtId}")
    public String delete(@PathVariable int districtId) {
        return districtService.delete(districtId);
    }

}
