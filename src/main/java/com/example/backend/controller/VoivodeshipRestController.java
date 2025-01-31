package com.example.backend.controller;


import com.example.backend.DTO.VoivodeshipResponse;
import com.example.backend.POJO.Voivodeship;
import com.example.backend.service.VoivodeshipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class VoivodeshipRestController {

    private VoivodeshipService voivodeshipService;

    public VoivodeshipRestController(VoivodeshipService theVoivodeshipService) {
        voivodeshipService = theVoivodeshipService;
    }


    @GetMapping("/voivodeships")
    public VoivodeshipResponse findAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "20", required = false) int pageSize
    ) {
        return voivodeshipService.findAll(pageNumber, pageSize);
    }

    @GetMapping("/voivodeships/{voivodeshipId}")
    public Voivodeship findById(@PathVariable int voivodeshipId) {
        return voivodeshipService.findById(voivodeshipId);
    }

    @PostMapping("/voivodeships")
    @ResponseStatus(HttpStatus.CREATED)
    public String save(@Valid @RequestBody Voivodeship theVoivodeship) {
        // just in case JSON is passed -> set id to 0
        // this is to force a save of new item instead of an update
        theVoivodeship.setVoivodeshipId(0);
        return voivodeshipService.save(theVoivodeship);
    }

    @PutMapping("/voivodeships")
    public String update(@Valid @RequestBody Voivodeship theVoivodeship) {
        return voivodeshipService.save(theVoivodeship);
    }

    @DeleteMapping("/voivodeships/{voivodeshipId}")
    public String delete(@PathVariable int voivodeshipId) {
        return voivodeshipService.delete(voivodeshipId);
    }

}
