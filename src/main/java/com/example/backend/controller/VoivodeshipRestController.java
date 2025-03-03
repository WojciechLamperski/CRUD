package com.example.backend.controller;


import com.example.backend.model.VoivodeshipResponse;
import com.example.backend.entity.VoivodeshipEntity;
import com.example.backend.service.VoivodeshipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class VoivodeshipRestController {

    private final VoivodeshipService voivodeshipService;

    public VoivodeshipRestController(VoivodeshipService theVoivodeshipService) {
        voivodeshipService = theVoivodeshipService;
    }


    @GetMapping("/voivodeships")
    public VoivodeshipResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "voivodeshipId", required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = "asc", required = false) String sortDirection
    ) {
        return voivodeshipService.findAll(pageNumber, pageSize, sortBy, sortDirection);
    }

    @GetMapping("/voivodeships/{voivodeshipId}")
    public VoivodeshipEntity findById(@PathVariable int voivodeshipId) {
        return voivodeshipService.findById(voivodeshipId);
    }

    @PostMapping("/voivodeships")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody VoivodeshipEntity theVoivodeship) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theVoivodeship.setVoivodeshipId(0);
        return voivodeshipService.save(theVoivodeship);
    }

    @PutMapping("/voivodeships")
    public String update(@Valid @RequestBody VoivodeshipEntity theVoivodeship) {
        return voivodeshipService.save(theVoivodeship);
    }

    @DeleteMapping("/voivodeships/{voivodeshipId}")
    public String delete(@PathVariable int voivodeshipId) {
        return voivodeshipService.delete(voivodeshipId);
    }

}
