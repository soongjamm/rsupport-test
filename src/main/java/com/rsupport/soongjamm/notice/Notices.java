package com.rsupport.soongjamm.notice;

import com.rsupport.soongjamm.notice.interfaces.NoticeDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notices {
	private List<NoticeDetail> notices;
	private int totalPages;
	private int current;
}
