package com.sampalmer.announcements

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient

class AnnouncementClient {

	def client

	AnnouncementClient(port) {
		client = new RESTClient("http://localhost:$port/")
		client.handler.failure = { resp, data ->
			resp.data = data
			return resp
		}
	}

	def sendCreateAnnouncement(body) {
		def resp = client.post(
				'path': 'announcements/',
				'requestContentType': ContentType.JSON,
				'body' : body
		)
		return resp.responseData
	}

	def sendLikeAnnouncement(id) {
		return postData("$id/like")
	}

	def sendDislikeAnnouncement(id) {
		return postData("$id/dislike")
	}

	def sendGetAnnouncement(id) {
		def resp = client.get(
				"path": "announcements/$id/"
		)
		return resp.responseData
	}

	def postData(path) {
		def resp = client.post(
				'path': "announcements/$path",
				'requestContentType': ContentType.JSON
		)
		return resp.responseData
	}
}
