package com.rsupport.soongjamm;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class InitData implements ApplicationRunner {

	private final NoticeService noticeService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 0; i < 50; i++) {
			noticeService.createNotice(new CreateNoticeTarget("title" + i, "김심심", "content" + i));
		}
	}
}
