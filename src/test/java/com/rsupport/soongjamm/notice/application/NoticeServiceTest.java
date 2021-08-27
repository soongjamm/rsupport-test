package com.rsupport.soongjamm.notice.application;

import com.rsupport.soongjamm.notice.application.impl.NoticeServiceImpl;
import com.rsupport.soongjamm.notice.domain.Notice;
import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.rsupport.soongjamm.notice.TestData.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NoticeServiceTest {

	@Mock
	private NoticeRepository noticeRepository;

	@InjectMocks
	private NoticeService noticeService = new NoticeServiceImpl(noticeRepository);

	@Test
	void create_notice_return_created_entity_when_succeed() {
	    //given
		CreateNoticeTarget target = createNoticeTarget().build();

		//when
		Notice notice = noticeService.createNotice(target);

		//then
		verify(noticeRepository).save(target.toEntity());
	}

	@Test
	void update_notice_return_updated_entity_when_succeed() {
		//given
		UpdateNoticeTarget target = updateNoticeTarget().build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));
		Notice expected = notice().title(target.getTitle()).content(target.getContent()).build();

		//when
		Notice notice = noticeService.updateNotice(target);

		//then
		assertThat(notice).isEqualTo(expected);
	}

	@Test
	void update_notice_throw_exception_when_title_size_is_over_maximum() {
		//given
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Notice.MAX_TITLE_LENGTH + 1; i++) {
			sb.append("X");
		}
		UpdateNoticeTarget target = updateNoticeTarget().title(sb.toString()).build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));

		//when
		//then
		assertThrows(IllegalArgumentException.class, () -> noticeService.updateNotice(target));
	}

	@Test
	void update_notice_throw_exception_when_content_size_is_over_maximum() {
		//given
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < Notice.MAX_TITLE_CONTENT + 1; i++) {
			sb.append("X");
		}
		UpdateNoticeTarget target = updateNoticeTarget().content(sb.toString()).build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));

		//when
		//then
		assertThrows(IllegalArgumentException.class, () -> noticeService.updateNotice(target));
	}

	@Test
	void update_notice_throw_exception_when_title_size_is_0() {
		//given
		UpdateNoticeTarget target = updateNoticeTarget().title("").build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));

		//when
		//then
		assertThrows(IllegalArgumentException.class, () -> noticeService.updateNotice(target));
	}

	@Test
	void update_notice_throw_exception_when_title_is_null() {
		//given
		UpdateNoticeTarget target = updateNoticeTarget().title(null).build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));

		//when
		//then
		assertThrows(IllegalArgumentException.class, () -> noticeService.updateNotice(target));
	}

	@Test
	void update_notice_throw_exception_when_content_size_is_0() {
		//given
		UpdateNoticeTarget target = updateNoticeTarget().content("").build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));

		//when
		//then
		assertThrows(IllegalArgumentException.class, () -> noticeService.updateNotice(target));
	}

	@Test
	void update_notice_throw_exception_when_content_is_null() {
		//given
		UpdateNoticeTarget target = updateNoticeTarget().content(null).build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice().build()));

		//when
		//then
		assertThrows(IllegalArgumentException.class, () -> noticeService.updateNotice(target));
	}

	@Test
	void delete_notice_succeed_if_validated() {
		//given
		Notice notice = notice().build();
		DeleteNoticeTarget target = DeleteNoticeTarget.builder().author(notice.getAuthor()).noticeId(notice.getId()).build();
		given(noticeRepository.findById(target.getNoticeId())).willReturn(Optional.of(notice));

		//when
		noticeService.deleteNotice(target);

		//then
		verify(noticeRepository).delete(notice);
	}
}