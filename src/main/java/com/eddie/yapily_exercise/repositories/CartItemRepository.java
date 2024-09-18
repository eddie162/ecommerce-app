package com.eddie.yapily_exercise.repositories;

import com.eddie.yapily_exercise.models.CartItem;
import com.eddie.yapily_exercise.models.CartItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, CartItemPK> {

}
