package com.mtb.booking.mtb.master.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "MOVIE")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "FieldHandler" })
@EntityListeners(value = { AuditingEntityListener.class })
public class Movie {

	@Id
	@Column(name = "MOVIEID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_generator")
	@SequenceGenerator(name = "movie_generator", sequenceName = "movie_sequence")
	private Long movieId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "RELEASEDATE")
	@Temporal(TemporalType.DATE)
	private Date releaseDate;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "DURATION")
	private Integer duration;

	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date modifiedDate;

}
