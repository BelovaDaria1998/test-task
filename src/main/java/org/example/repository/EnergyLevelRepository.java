package org.example.repository;

import org.example.entity.EnergyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnergyLevelRepository extends JpaRepository<EnergyLevel, Long> {

    Optional<EnergyLevel> findFirstByIsin(String isin);
}
