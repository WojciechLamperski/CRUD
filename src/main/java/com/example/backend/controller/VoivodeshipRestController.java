package com.example.backend.controller;


import com.example.backend.model.VoivodeshipModel;
import com.example.backend.model.VoivodeshipResponse;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.service.VoivodeshipService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class VoivodeshipRestController {

    private Logger logger = LoggerFactory.getLogger(VoivodeshipRestController.class);

    private final VoivodeshipService voivodeshipService;

    public VoivodeshipRestController(VoivodeshipService theVoivodeshipService) {
        voivodeshipService = theVoivodeshipService;
    }


    @GetMapping("/voivodeships")
    @ResponseStatus(HttpStatus.OK)
    public VoivodeshipResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "voivodeshipId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        logger.info("find all voivodeships incoming request");
        return voivodeshipService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/voivodeships/{voivodeshipId}")
    @ResponseStatus(HttpStatus.OK)
    public VoivodeshipModel findById(@PathVariable int voivodeshipId) {
        logger.info("find voivodeship by Id incoming request");
        return voivodeshipService.findById(voivodeshipId);
    }

    @PostMapping("/voivodeships")
    @ResponseStatus(HttpStatus.CREATED)
    public VoivodeshipModel save(@Valid @RequestBody VoivodeshipEntity theVoivodeship) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        logger.info("save voivodeship incoming request {}", theVoivodeship);
        theVoivodeship.setVoivodeshipId(0);
        return voivodeshipService.save(theVoivodeship);
    }

    @PutMapping("/voivodeships")
    @ResponseStatus(HttpStatus.OK)
    public VoivodeshipModel update(@Valid @RequestBody VoivodeshipEntity theVoivodeship) {
        logger.info("update voivodeship incoming request {}", theVoivodeship);
        return voivodeshipService.save(theVoivodeship);
    }

    @DeleteMapping("/voivodeships/{voivodeshipId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String delete(@PathVariable int voivodeshipId) {
        logger.info("delete voivodeship incoming request");
        return voivodeshipService.delete(voivodeshipId);
    }

}
