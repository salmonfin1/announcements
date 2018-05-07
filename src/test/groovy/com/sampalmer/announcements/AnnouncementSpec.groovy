package com.sampalmer.announcements

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(classes = AnnouncementsApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnnouncementSpec extends Specification {

	@Value('${local.server.port}')
	private def port

	AnnouncementClient client

	def setup() {
		client = new AnnouncementClient(port)
	}

	def "When I create an announcement, an announcement with a non null id is returned"() {
		when:
			def announcement = client.sendCreateAnnouncement(['description' : 'An announcement'])
		then:
			announcement.id != null
	}

	def "When I like an announcement the likes are incremented by one"() {
		given:
			def announcement = client.sendCreateAnnouncement(['description' : 'An announcement'])
			def id = announcement.id
		when:
			def response = client.sendLikeAnnouncement(id)
		then:
			response.id == id
			response.likes == announcement.likes + 1
	}

	def "When I dislike an announcement the dislikes are incremented by one"() {
		given:
			def announcement = client.sendCreateAnnouncement(['description' : 'An announcement'])
			def id = announcement.id
		when:
			def response = client.sendDislikeAnnouncement(id)
		then:
			response.id == id
			response.dislikes == announcement.dislikes + 1
	}

	def "When I get an announcement with a valid id, I get the announcement"() {
		given:
			def announcement = client.sendCreateAnnouncement(['description' : 'An announcement'])
		when:
			def response = client.sendGetAnnouncement(announcement.id)
		then:
			response.id == announcement.id
			response.description == 'An announcement'
	}

	def "When I get an announcement with an invalid id, I get an error"() {
		given:
			def announcement = client.sendCreateAnnouncement(['description' : 'An announcement'])
			def id = announcement.id + 1
		when:
			def response = client.sendGetAnnouncement(id)
		then:
			response.message == "Cannot find an announcement with id $id"
	}
}
