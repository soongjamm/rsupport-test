package com.rsupport.soongjamm.common;

import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseTimeEntity {

	@CreatedDate
	private LocalDateTime createDate;

	@LastModifiedDate
	private LocalDateTime lastModifiedDate;

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public LocalDateTime getLastModifiedDate() {
		return lastModifiedDate;
	}
}
