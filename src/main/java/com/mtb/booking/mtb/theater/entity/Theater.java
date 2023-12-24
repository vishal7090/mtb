package com.mtb.booking.mtb.theater.entity;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "THEATER")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "FieldHandler" })
@EntityListeners(value = { AuditingEntityListener.class })
public class Theater {

	@Id
	@Column(name = "THEATERID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "theater_generator")
	@SequenceGenerator(name = "theater_generator", sequenceName = "theater_sequence")
	private Long theaterId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "CITYCODE")
	private String cityCode;

	@Column(name = "STATUS")
	private Boolean status;

}
