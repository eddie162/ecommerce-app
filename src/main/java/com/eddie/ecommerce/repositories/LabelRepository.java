package com.eddie.ecommerce.repositories;

import com.eddie.ecommerce.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LabelRepository extends JpaRepository<Label, Long> {

    Set<Label> findAllByLabelNameIn(Set<String> labels);
}