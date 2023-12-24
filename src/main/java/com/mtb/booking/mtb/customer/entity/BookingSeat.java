package com.mtb.booking.mtb.customer.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BOOKINGSEAT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "FieldHandler" })
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "bookingSeatId")
public class BookingSeat {

	@Id
	@Column(name = "BOOKINGSEATID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookingseat_generator")
	@SequenceGenerator(name = "bookingseat_generator", sequenceName = "bookingseat_sequence")
	private Long bookingSeatId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BOOKINGID")
	@JsonIdentityReference(alwaysAsId = true)
	private Booking booking;

	@JoinColumn(name = "SHOWSEATID")
	private Long showSeatId;

	@Column(name = "status")
	private int status;

}
