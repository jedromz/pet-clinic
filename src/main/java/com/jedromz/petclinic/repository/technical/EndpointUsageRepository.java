package com.jedromz.petclinic.repository.technical;

import com.jedromz.petclinic.model.technical.EndpointUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EndpointUsageRepository extends JpaRepository<EndpointUsage, Integer> {
}
