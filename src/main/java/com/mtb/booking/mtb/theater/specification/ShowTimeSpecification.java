package com.mtb.booking.mtb.theater.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mtb.booking.mtb.theater.entity.Screen;
import com.mtb.booking.mtb.theater.entity.ShowTime;
import com.mtb.booking.mtb.theater.entity.Theater;
import com.mtb.booking.mtb.theater.model.request.ShowTimeSearchRequest;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;

@Component
public class ShowTimeSpecification {

	public Specification<ShowTime> searchShowTimes(ShowTimeSearchRequest showTimeSearchRequest) {
		return (root, query, criteriaBuilder) -> {

			List<Predicate> andPredicates = new ArrayList<>();

			if (Objects.nonNull(showTimeSearchRequest.getDate())) {
				andPredicates.add(criteriaBuilder.equal(root.get("date"), showTimeSearchRequest.getDate()));
			}

			if (showTimeSearchRequest.getMovieId() != 0) {
				andPredicates.add(criteriaBuilder.equal(root.get("movieId"), showTimeSearchRequest.getMovieId()));
			}
			if (showTimeSearchRequest.getScreenId() != 0) {
				Join<ShowTime, Screen> screenJoin = root.join("screen");
				andPredicates
						.add(criteriaBuilder.equal(screenJoin.get("screenId"), showTimeSearchRequest.getScreenId()));

				// TODO - Need to Check
				if (showTimeSearchRequest.getTheaterId() != 0) {
					Join<Screen, Theater> theaterJoin = screenJoin.join("threater");
					andPredicates.add(criteriaBuilder.equal(theaterJoin.get("theaterJoin"),
							showTimeSearchRequest.getTheaterId()));
				}

			}

			// TODO - Need to Check
			if (showTimeSearchRequest.getScreenId() == 0 && showTimeSearchRequest.getTheaterId() != 0) {
				Join<Screen, Theater> theaterJoin = root.join("screen").join("threater");
				andPredicates.add(
						criteriaBuilder.equal(theaterJoin.get("theaterJoin"), showTimeSearchRequest.getTheaterId()));
			} else if (StringUtils.hasText(showTimeSearchRequest.getCityCode())) {

				// TODO - Remove else then required to write getJoins code
				Join<Screen, Theater> theaterJoin = root.join("screen").join("threater");
				andPredicates
						.add(criteriaBuilder.equal(theaterJoin.get("cityCode"), showTimeSearchRequest.getCityCode()));
			}

			return criteriaBuilder.and(andPredicates.toArray(new Predicate[andPredicates.size()]));
		};
	}

}
