package com.rsupport.soongjamm.notice.interfaces;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rsupport.soongjamm.notice.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class NoticeDetail {
	private Long id;
	private String title;
	private String author;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime lastModifiedDate;
	private String content;

	public static NoticeDetail from(Notice notice) {
		return new NoticeDetail(notice.getId(), notice.getTitle(), notice.getAuthor(), notice.getCreateDate(), notice.getLastModifiedDate(), notice.getContent());
	}
}
