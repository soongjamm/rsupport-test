package com.rsupport.soongjamm.notice.application;

import com.rsupport.soongjamm.notice.Notices;
import com.rsupport.soongjamm.notice.domain.Notice;
import org.springframework.data.domain.PageRequest;

public interface NoticeService {

	Notice getANotice(Long noticeId);

	Notices getNotices(PageRequest pageRequest);

	Notice createNotice(CreateNoticeTarget target);

	Notice updateNotice(UpdateNoticeTarget toTarget);

	void deleteNotice(DeleteNoticeTarget serviceTarget);
}
