package com.rsupport.soongjamm.notice.application.impl;

import com.rsupport.soongjamm.notice.Notices;
import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.DeleteNoticeTarget;
import com.rsupport.soongjamm.notice.application.UpdateNoticeTarget;
import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	public Notice getANotice(Long noticeId) {
		return noticeRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException("잘못된 공지번호 입니다."));
	}

	@Override
	public Page<Notice> getNotices(Pageable pageRequest) {
		return noticeRepository.findAll(pageRequest);
	}

	@Override
	public Notice createNotice(CreateNoticeTarget target) {
		Notice notice = target.toEntity();
		return noticeRepository.save(notice);
	}

	@Override
	@Transactional
	public Notice updateNotice(UpdateNoticeTarget target) {
		Notice notice = noticeRepository.findById(target.getNoticeId()).orElseThrow(() -> new IllegalArgumentException("잘못된 공지번호 입니다."));
		notice.update(target.getTitle(), target.getContent(), target.getAuthor());
		return notice;
	}

	@Override
	public void deleteNotice(DeleteNoticeTarget target) {
		Notice notice = noticeRepository.findById(target.getNoticeId()).orElseThrow(() -> new IllegalArgumentException("잘못된 공지번호 입니다."));
		notice.validateAuthor(target.getAuthor());
		noticeRepository.delete(notice);
	}
}
