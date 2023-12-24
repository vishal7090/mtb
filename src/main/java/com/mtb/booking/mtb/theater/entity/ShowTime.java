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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "SHOWTIME")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "FieldHandler" })
@EntityListeners(value = { AuditingEntityListener.class })
public class ShowTime {

	@Id
	@Column(name = "SHOWTIMEID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "showtime_generator")
	@SequenceGenerator(name = "showtime_generator", sequenceName = "showtime_sequence")
	private Long showTimeId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DATE")
	@Temporal(TemporalType.DATE)
	private Date date;

	@Column(name = "STARTTIME")
	@Temporal(TemporalType.TIME)
	private Date startTime;

	@Column(name = "ENDTIME")
	@Temporal(TemporalType.TIME)
	private Date endTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCREENID")
	private Screen screen;

	@Column(name = "MOVIEID")
	private Long movieId;

	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date modifiedDate;

}
