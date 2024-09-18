package com.eddie.yapily_exercise.service.mappers;

public interface DtoEntityMapper <D, E> {
    E dtoToEntity(D dto);
    D entityToDto(E entity);
}
