package com.eddie.ecommerce.repositories;

import com.eddie.ecommerce.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    Set<Label> findAllByLabelNameIn(Set<String> labels);
}