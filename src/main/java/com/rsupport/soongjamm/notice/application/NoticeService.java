package com.rsupport.soongjamm.notice.application;

import com.rsupport.soongjamm.notice.domain.Notice;

public interface NoticeService {

	Notice createNotice(CreateNoticeTarget target);

	Notice updateNotice(UpdateNoticeTarget toTarget);
}
