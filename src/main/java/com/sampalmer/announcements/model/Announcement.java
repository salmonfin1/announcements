package com.sampalmer.announcements.model;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ANNOUNCEMENT")
public class Announcement {

	@Id
	@Column(name = "ID")
	@GeneratedValue
	private Long id;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "LIKES")
	private Long likes;

	@Column(name = "DISLIKES")
	private Long dislikes;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLikes() {
		return Optional.ofNullable(likes)
				.orElse(0L);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getDislikes() {
		return Optional.ofNullable(dislikes)
				.orElse(0L);
	}

	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}

	public void like() {
		this.setLikes(this.getLikes()+1);
	}

	public void dislike() {
		this.setDislikes(this.getDislikes()+1);
	}
}
