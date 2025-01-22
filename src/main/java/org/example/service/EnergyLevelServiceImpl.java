package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.EnergyLevelDto;
import org.example.entity.EnergyLevel;
import org.example.mapper.EnergyLevelMapper;
import org.example.repository.EnergyLevelRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnergyLevelServiceImpl implements EnergyLevelService {

    private final EnergyLevelRepository energyLevelRepository;
    private final EnergyLevelMapper energyLevelMapper;

    @Override
    public List<EnergyLevelDto> getEnergyLevels() {
        return energyLevelMapper.toDtoList(energyLevelRepository.findAll());
    }

    @Override
    public EnergyLevelDto getEnergyLevelByIsin(String isin) {
        EnergyLevel energyLevel = energyLevelRepository.findFirstByIsin(isin)
                .orElseThrow(() -> new EntityNotFoundException("Energy level not found by isin = " + isin));
        return energyLevelMapper.toDto(energyLevel);
    }
}
