package org.example.mapper;

import org.example.dto.EnergyLevelDto;
import org.example.entity.EnergyLevel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EnergyLevelMapper {

    List<EnergyLevelDto> toDtoList(List<EnergyLevel> energyLevels);
    EnergyLevelDto toDto(EnergyLevel energyLevel);
}
