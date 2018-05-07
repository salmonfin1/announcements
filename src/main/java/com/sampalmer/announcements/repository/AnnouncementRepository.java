package com.sampalmer.announcements.repository;

import com.sampalmer.announcements.model.Announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {


}
