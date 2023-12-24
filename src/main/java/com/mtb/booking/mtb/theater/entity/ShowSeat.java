package com.mtb.booking.mtb.theater.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

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
@Table(name = "SHOWSEAT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "FieldHandler" })
@EntityListeners(value = { AuditingEntityListener.class })
public class ShowSeat {

	@Id
	@Column(name = "SHOWSEATID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "showseat_generator")
	@SequenceGenerator(name = "showseat_generator", sequenceName = "showseat_sequence")
	private Long showSeatId;

	@Column(name = "ROWSEAT")
	private String rowSeat;

	@Column(name = "COLSEAT")
	private String colSeat;

	@Column(name = "PRICE")
	private Double price;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SHOWID")
	private ShowTime showTime;

	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date modifiedDate;

}
