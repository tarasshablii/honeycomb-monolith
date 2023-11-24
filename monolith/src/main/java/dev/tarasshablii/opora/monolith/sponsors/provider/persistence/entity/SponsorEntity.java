package dev.tarasshablii.opora.monolith.sponsors.provider.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "sponsors")
public class SponsorEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String userName;
	private String name;
	private String description;
	private String media;
	@ManyToMany
	private List<ContactEntity> contacts;

}