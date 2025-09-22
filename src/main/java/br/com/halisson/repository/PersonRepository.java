package br.com.halisson.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.halisson.domain.PersonEntity;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
}
