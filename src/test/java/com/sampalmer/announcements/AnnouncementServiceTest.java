package com.sampalmer.announcements;

import com.sampalmer.announcements.exception.CannotFindAnnouncementException;
import com.sampalmer.announcements.model.Announcement;
import com.sampalmer.announcements.repository.AnnouncementRepository;
import com.sampalmer.announcements.service.AnnouncementService;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AnnouncementServiceTest {

	private AnnouncementRepository repository;

	private AnnouncementService service;

	@Before
	public void setup() {
		repository = Mockito.mock(AnnouncementRepository.class);
		service = new AnnouncementService(repository);
	}

	@Test
	public void testCreateAnnouncement() {
		Announcement announcement = new Announcement();
		announcement.setDescription("Description");
		Announcement returnAnnouncement = new Announcement();
		returnAnnouncement.setId(1L);
		when(repository.save(announcement)).thenReturn(returnAnnouncement);
		Announcement created = service.createAnnouncement(announcement);
		assertEquals(created.getId(), returnAnnouncement.getId());
		verify(repository, times(1)).save(announcement);
	}

	@Test
	public void testLikeAnnouncement() {
		Announcement announcement = setupRepository();
		Announcement response = service.likeAnnouncement(1L);

		verifyResponse(response, response.getLikes(), announcement.getLikes());
	}

	@Test
	public void testDislikeAnnouncement() {
		Announcement announcement = setupRepository();
		Announcement response = service.likeAnnouncement(1L);

		verifyResponse(response, response.getDislikes(), announcement.getDislikes());
	}

	@Test
	public void testGetAnnouncement() {
		try {
			service.getAnnouncement(123L);
		} catch (CannotFindAnnouncementException e) {
			assertEquals(e.getMessage(), "Cannot find an announcement with id 123");
		}
		Announcement announcement = setupRepository();
		Announcement response = service.getAnnouncement(1L);
		assertEquals(announcement, response);
		verify(repository, times(1)).findById(1L);
	}

	private Announcement setupRepository() {
		Announcement announcement = new Announcement();
		announcement.setId(1L);
		announcement.setLikes(1L);
		when(repository.findById(1L)).thenReturn(Optional.of(announcement));
		when(repository.save(any())).thenReturn(announcement);
		return announcement;
	}

	private void verifyResponse(Announcement response, Long firstNumber, Long secondNumber) {
		assertEquals(firstNumber, secondNumber);
		verify(repository, times(1)).save(response);
		verify(repository, times(1)).findById(1L);
	}


}
