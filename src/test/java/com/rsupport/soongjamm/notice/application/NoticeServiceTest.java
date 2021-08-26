package com.rsupport.soongjamm.notice.application;

import com.rsupport.soongjamm.notice.application.impl.NoticeServiceImpl;
import com.rsupport.soongjamm.notice.domain.Notice;
import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.rsupport.soongjamm.notice.TestData.createNoticeTarget;
import static org.assertj.core.api.Assertions.assertThat;

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
		assertThat(notice).isInstanceOf(Notice.class);
	}
}