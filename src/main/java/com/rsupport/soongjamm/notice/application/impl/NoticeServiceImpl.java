package com.rsupport.soongjamm.notice.application.impl;

import com.rsupport.soongjamm.notice.Notices;
import com.rsupport.soongjamm.notice.application.CreateNoticeTarget;
import com.rsupport.soongjamm.notice.application.DeleteNoticeTarget;
import com.rsupport.soongjamm.notice.application.UpdateNoticeTarget;
import com.rsupport.soongjamm.notice.domain.NoticeRepository;
import com.rsupport.soongjamm.notice.application.NoticeService;
import com.rsupport.soongjamm.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoticeServiceImpl implements NoticeService {

	private NoticeRepository noticeRepository;

	public NoticeServiceImpl(NoticeRepository noticeRepository) {
		this.noticeRepository = noticeRepository;
	}

	@Override
	public Notices getNotices(PageRequest pageRequest) {
		Page<Notice> found = noticeRepository.findAll(pageRequest);
		List<Notice> collect = found.stream().collect(Collectors.toList());
		return new Notices(collect, found.getTotalPages(), pageRequest.getPageNumber());
	}

	@Override
	public Notice createNotice(CreateNoticeTarget target) {
		Notice notice = target.toEntity();
		return noticeRepository.save(notice);
	}

	@Override
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
