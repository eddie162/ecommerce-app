package com.eddie.yapily_exercise.service.mappers;

/**
 * Helper interface to convert DTO (user-facing) to Model classes and vice versa
 * @param <D>
 * @param <E>
 */
public interface DtoEntityMapper <D, E> {
    E dtoToEntity(D dto);
    D entityToDto(E entity);
}
