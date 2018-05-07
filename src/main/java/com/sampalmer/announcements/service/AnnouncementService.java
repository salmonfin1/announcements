package com.sampalmer.announcements.service;

import com.sampalmer.announcements.exception.CannotFindAnnouncementException;
import com.sampalmer.announcements.model.Announcement;
import com.sampalmer.announcements.repository.AnnouncementRepository;

import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class AnnouncementService {

	private final AnnouncementRepository repository;

	public AnnouncementService(AnnouncementRepository repository) {
		this.repository = repository;
	}

	public Announcement createAnnouncement(Announcement announcement) {
		return repository.save(announcement);
	}

	public Announcement likeAnnouncement(Long id) {
		return updateLikes(id, Announcement::like);
	}

	public Announcement dislikeAnnouncement(Long id) {
		return updateLikes(id, Announcement::dislike);
	}

	public Announcement getAnnouncement(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new CannotFindAnnouncementException(id));
	}

	private Announcement updateLikes(Long id, Consumer<Announcement> function) {
		Announcement announcement = getAnnouncement(id);
		function.accept(announcement);
		return repository.save(announcement);
	}
}
