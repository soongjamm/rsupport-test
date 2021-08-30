package com.rsupport.soongjamm.notice.application;

import com.rsupport.soongjamm.notice.domain.Notice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NoticeService {

	Notice getANotice(Long noticeId);

	Page<Notice> getNotices(Pageable pageRequest);

	Notice createNotice(CreateNoticeTarget target);

	Notice updateNotice(UpdateNoticeTarget toTarget);

	void deleteNotice(DeleteNoticeTarget serviceTarget);
}
