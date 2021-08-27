package com.rsupport.soongjamm.notice.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class NoticeRepositoryTest {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private NoticeRepository noticeRepository;

	@Test
	void saved__notice_in_persistence_has_same_content() {
		//given
		Notice notice = new Notice("제목", "작성자", "내용");
		Notice saved = noticeRepository.saveAndFlush(notice);
		entityManager.clear();

		//when
		Notice found = noticeRepository.findById(saved.getId()).get();

		//then
		assertThat(found).isEqualTo(saved);
	}

	@Test
	void timeBaseEntity_create_date_automatically() {
		//given
		Notice notice = new Notice("제목", "작성자", "내용");
		Notice saved = noticeRepository.saveAndFlush(notice);
		entityManager.clear();

		//when
		Notice found = noticeRepository.findById(saved.getId()).get();

		//then
		assertThat(found.getCreateDate()).isNotNull();
		assertThat(found.getLastModifiedDate()).isNotNull();
	}
}