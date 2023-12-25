package com.mtb.booking.mtb.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.mtb.booking.mtb.theater.entity.ShowSeat;

@Repository
public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long>, JpaSpecificationExecutor<ShowSeat> {

}
