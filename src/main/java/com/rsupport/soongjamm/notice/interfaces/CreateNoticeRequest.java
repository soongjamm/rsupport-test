package com.rsupport.soongjamm.notice.interfaces;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CreateNoticeRequest {
	private String title;
	private String author;
	private String content;

	public CreateNoticeTarget toTarget() {
		return new CreateNoticeTarget(this.title, this.author, this.getContent());
	}
}
