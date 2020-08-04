package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.request.PhotoRequestDto;

public interface PhotoService {
	public List<String> registerPhoto(PhotoRequestDto photo);
}
