package com.sampalmer.announcements.exception;

public class CannotFindAnnouncementException extends RuntimeException {

	public CannotFindAnnouncementException(Long id) {
		super("Cannot find an announcement with id " + id);
	}
}
