package dev.tarasshablii.opora.monolith.sponsors.endpoint.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class SponsorDto {
	private UUID id;
	private String userName;
	private String name;
	private String description;
	private UUID media;
	private List<Contact> contacts;

	@Data
	public static class Contact {
		private UUID id;
		private String firstName;
		private String lastName;
		private List<Phone> phones;
		private String email;
		private List<Link> links;
	}

	@Data
	public static class Phone {
		private String number;
		private List<PhoneType> types;
	}

	public enum PhoneType {
		CELLULAR, VIBER, TELEGRAM, WHATSAPP, SIGNAL
	}

	@Data
	public static class Link {
		private String url;
		private PlatformType platform;
	}

	public enum PlatformType {
		FACEBOOK, X, INSTAGRAM, TELEGRAM, WEBSITE
	}
}
