package com.mtb.booking.mtb.theater.entity;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Table(name = "SCREEN")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "FieldHandler" })
@EntityListeners(value = { AuditingEntityListener.class })
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "screenId")
public class Screen {

	@Id
	@Column(name = "SCREENID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "screen_generator")
	@SequenceGenerator(name = "screen_generator", sequenceName = "screen_sequence")
	private Long screenId;

	@Column(name = "NAME")
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "THEATERID")
	private Theater threater;

	@CreatedDate
	private Date createdDate;

	@LastModifiedDate
	private Date modifiedDate;

}
