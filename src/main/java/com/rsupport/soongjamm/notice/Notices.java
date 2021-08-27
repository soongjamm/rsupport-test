package com.rsupport.soongjamm.notice;

import com.rsupport.soongjamm.notice.domain.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notices {
	private List<Notice> notices;
	private int total;
	private int current;
}
