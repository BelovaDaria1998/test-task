package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.EnergyLevelDto;
import org.example.service.EnergyLevelService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/elvl")
public class EnergyLevelController {

    private final EnergyLevelService energyLevelService;

    @GetMapping
    public List<EnergyLevelDto> getEnergyLevels() {
        return energyLevelService.getEnergyLevels();
    }

    @GetMapping("/{isin}")
    public EnergyLevelDto getEnergyLevelByIsin(@PathVariable("isin") String isin) {
        return energyLevelService.getEnergyLevelByIsin(isin);
    }
}
