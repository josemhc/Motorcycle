package com.Motocicletas.repository;

import com.Motocicletas.model.Sale;
import org.springframework.data.repository.CrudRepository;

public interface ISaleRepository extends CrudRepository<Sale, Long> {
}
