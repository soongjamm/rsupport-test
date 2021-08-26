package com.rsupport.soongjamm.notice.domain;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
public class Notice {
	private static final int MAX_TITLE_LENGTH = 50;
	private static final int MAX_TITLE_CONTENT = 400;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String author;
	private LocalDateTime lastModifiedAt;
	private String content;

	public Notice() {
	}

	public Notice(String title, String author, LocalDateTime lastModifiedAt, String content) {
		validateNotice(title, content);
		this.title = title;
		this.author = author;
		this.lastModifiedAt = lastModifiedAt;
		this.content = content;
	}

	private void validateNotice(String title, String content) {
		if (StringUtils.isAnyBlank(title, content)) {
			throw new IllegalArgumentException("제목이나 내용에 공백이 올 수 없습니다.");
		}
		if (title.length() > MAX_TITLE_LENGTH) {
			throw new IllegalArgumentException("제목의 최대 길이를 초과하였습니다.");
		}
		if (content.length() > MAX_TITLE_CONTENT) {
			throw new IllegalArgumentException("내용의 최대 길이를 초과하였습니다.");
		}
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Notice notice = (Notice) o;
		return id.equals(notice.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public String toString() {
		return "Notice{" +
				"title='" + title + '\'' +
				", author='" + author + '\'' +
				", lastModifiedAt=" + lastModifiedAt +
				", content='" + content + '\'' +
				'}';
	}
}