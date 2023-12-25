package com.mtb.booking.mtb.theater.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.mtb.booking.mtb.theater.entity.Screen;
import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.model.request.ScreenSearchRequest;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Component
public class ScreenSpecification {

	public Specification<Screen> searchScreens(ScreenSearchRequest screenSearchRequest) {
		return (root, query, criteriaBuilder) -> {

			List<Predicate> andPredicates = new ArrayList<>();

			if (screenSearchRequest.getTheaterId() != 0) {
				Join<Screen, Theater> theaterJoin = root.join("theater");
				andPredicates
						.add(criteriaBuilder.equal(theaterJoin.get("theaterId"), screenSearchRequest.getTheaterId()));
			}
			return criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
		};
	}

}
