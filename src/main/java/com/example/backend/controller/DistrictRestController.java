package com.example.backend.controller;

import com.example.backend.model.DistrictModel;
import com.example.backend.model.DistrictRequest;
import com.example.backend.model.DistrictResponse;
import com.example.backend.entity.DistrictEntity;
import com.example.backend.service.DistrictService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class DistrictRestController {

    private Logger logger = LoggerFactory.getLogger(DistrictRestController.class);

    private final DistrictService districtService;

    public DistrictRestController(DistrictService theDistrictService) {
        districtService = theDistrictService;
    }

    @GetMapping("/districts")
    @ResponseStatus(HttpStatus.OK)
    public DistrictResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "districtId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all districts incoming request");
        return districtService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/voivodeships/{voivodeshipId}/districts")
    @ResponseStatus(HttpStatus.OK)
    public DistrictResponse findAllInVoivodeship(
            @PathVariable int voivodeshipId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "districtId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all districts in a specified voivodeship incoming request");
        return districtService.findAllInVoivodeship(voivodeshipId, pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/districts/{districtId}")
    @ResponseStatus(HttpStatus.OK)
    public DistrictModel findById(@PathVariable int districtId) {
        logger.info("find district by Id incoming request");
        return districtService.findById(districtId);
    }

    @PostMapping("/districts")
    @ResponseStatus(HttpStatus.CREATED)
    public DistrictModel save(@Valid @RequestBody DistrictRequest theDistrict) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        logger.info("save district incoming request {}", theDistrict);
        theDistrict.setDistrictId(0);
        return districtService.save(theDistrict);
    }

    @PutMapping("/districts")
    @ResponseStatus(HttpStatus.OK)
    public DistrictModel update(@Valid @RequestBody DistrictRequest theDistrict) {
        logger.info("update district incoming request {}", theDistrict);
        return districtService.save(theDistrict);
    }

    @DeleteMapping("/districts/{districtId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int districtId) {
        logger.info("delete district incoming request");
        districtService.delete(districtId);
    }

}
