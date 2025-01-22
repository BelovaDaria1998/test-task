package org.example.service;

import org.example.dto.EnergyLevelDto;

import java.util.List;

public interface EnergyLevelService {

    List<EnergyLevelDto> getEnergyLevels();
    EnergyLevelDto getEnergyLevelByIsin(String isin);
}
