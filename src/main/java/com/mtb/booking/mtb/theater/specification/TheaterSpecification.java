package com.mtb.booking.mtb.theater.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.model.request.TheaterSearchRequest;

import jakarta.persistence.criteria.Predicate;

@Component
public class TheaterSpecification {

	public Specification<Theater> searchTheaters(TheaterSearchRequest theaterSearchRequest) {
		return (root, query, criteriaBuilder) -> {

			List<Predicate> andPredicates = new ArrayList<>();

			if (StringUtils.hasText(theaterSearchRequest.getCityCode())) {
				andPredicates.add(criteriaBuilder.equal(root.get("cityCode"), theaterSearchRequest.getCityCode()));
			}
			return criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
		};
	}

}
