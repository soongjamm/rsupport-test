package com.rsupport.soongjamm.notice.application.impl;

import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.UpdateNoticeTarget;
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

	@Override
	public Notice updateNotice(UpdateNoticeTarget target) {
		Notice notice = noticeRepository.findById(target.getNoticeId()).orElseThrow(() -> new IllegalArgumentException("잘못된 공지번호 입니다."));
		notice.update(target.getTitle(), target.getContent(), target.getAuthor());
		return notice;
	}
}
