package com.eddie.yapily_exercise.repositories;

import com.eddie.yapily_exercise.models.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LabelRepository extends JpaRepository<Label, Long> {

    List<Label> findAllByLabelNameIn(List<String> labels);
}