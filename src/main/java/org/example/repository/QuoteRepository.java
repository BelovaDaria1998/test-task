package org.example.repository;

import org.example.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public  interface QuoteRepository extends JpaRepository<Quote, Long> {
}
