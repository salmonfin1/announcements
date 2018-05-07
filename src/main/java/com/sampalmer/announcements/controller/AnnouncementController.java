package com.sampalmer.announcements.controller;

import com.sampalmer.announcements.exception.CannotFindAnnouncementException;
import com.sampalmer.announcements.model.Announcement;
import com.sampalmer.announcements.service.AnnouncementService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class AnnouncementController {

	private final AnnouncementService service;

	public AnnouncementController(AnnouncementService service) {
		this.service = service;
	}

	@ExceptionHandler(CannotFindAnnouncementException.class)
	public @ResponseBody ResponseEntity<Map<String, String>> handleMissingAnnouncement(CannotFindAnnouncementException e) {
		Map<String, String> response = Collections.singletonMap("message", e.getMessage());
		return ResponseEntity.ok(response);
	}

	@RequestMapping(value = "/announcements/", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Announcement> createAnnouncement(@RequestBody Announcement announcement) {
		return ResponseEntity.ok(service.createAnnouncement(announcement));
	}

	@RequestMapping(value = "/announcements/{id}/like", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Announcement> likeAnnouncement(@PathVariable Long id) {
		Announcement announcement = service.likeAnnouncement(id);
		return ResponseEntity.ok(announcement);
	}

	@RequestMapping("/announcements/{id}/dislike")
	public @ResponseBody ResponseEntity<Announcement> dislikeAnnouncement(@PathVariable Long id) {
		Announcement announcement = service.dislikeAnnouncement(id);
		return ResponseEntity.ok(announcement);
	}

	@RequestMapping("/announcements/{id}")
	public @ResponseBody ResponseEntity<Announcement> getAnnouncement(@PathVariable Long id) {
		Announcement announcement = service.getAnnouncement(id);
		return ResponseEntity.ok(announcement);
	}
}
