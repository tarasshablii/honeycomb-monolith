package dev.tarasshablii.monolith.opora.sponsors.provider.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "contacts")
public class ContactEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String firstName;
	private String lastName;
	private String email;
	@ElementCollection
	private List<PhoneEmbeddable> phones;
	@ElementCollection
	private List<LinkEmbeddable> links;
}