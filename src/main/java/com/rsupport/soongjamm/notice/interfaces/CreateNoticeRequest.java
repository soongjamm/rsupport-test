package com.rsupport.soongjamm.notice.interfaces;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class CreateNoticeRequest {
	private String title;
	private String author;
	private LocalDateTime lastModifiedAt;
	private String content;

	public CreateNoticeRequest(String title, String author, String content) {
		this.title = title;
		this.author = author;
		this.lastModifiedAt = LocalDateTime.now();
		this.content = content;
	}

	@Builder
	CreateNoticeRequest(String title, String author, String content, Clock clock) {
		this.title = title;
		this.author = author;
		this.lastModifiedAt = LocalDateTime.now(clock);
		this.content = content;
	}

	public CreateNoticeTarget toTarget() {
		return new CreateNoticeTarget(this.title, this.author, this.lastModifiedAt, this.getContent());
	}
}
