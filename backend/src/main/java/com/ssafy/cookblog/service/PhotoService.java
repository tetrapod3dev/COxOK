package com.ssafy.cookblog.service;

import java.util.List;

import com.ssafy.cookblog.dto.request.PhotoRegisterRequestDto;

public interface PhotoService {
	public List<String> registerPhoto(PhotoRegisterRequestDto photo);
}
