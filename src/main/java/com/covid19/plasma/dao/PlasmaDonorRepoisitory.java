package com.covid19.plasma.dao;

import com.covid19.plasma.model.PlasmaDonor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlasmaDonorRepoisitory extends CrudRepository<PlasmaDonor, Long> {}
