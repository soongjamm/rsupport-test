package com.rsupport.soongjamm.notice.application.impl;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.domain.Notice;
import org.springframework.stereotype.Service;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	public Notice createNotice(CreateNoticeTarget target) {
		Notice notice = target.toEntity();
		noticeRepository.save(notice);
		return notice;
	}
}
