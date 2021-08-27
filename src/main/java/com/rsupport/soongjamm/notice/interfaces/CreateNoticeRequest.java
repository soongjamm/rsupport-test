package com.rsupport.soongjamm.notice.interfaces;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateNoticeRequest {
	private String title;
	private String author;
	private String content;

	@Builder
	CreateNoticeRequest(String title, String author, String content) {
		this.title = title;
		this.author = author;
		this.content = content;
	}

	public CreateNoticeTarget toTarget() {
		return new CreateNoticeTarget(this.title, this.author, this.getContent());
	}
}
