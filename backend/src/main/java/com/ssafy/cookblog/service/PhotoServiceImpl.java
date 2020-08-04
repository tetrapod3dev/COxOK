package com.ssafy.cookblog.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.cookblog.dto.RecipePhotoDto;
import com.ssafy.cookblog.dto.request.PhotoRequestDto;

@Service
public class PhotoServiceImpl implements PhotoService {
	
	@Autowired
	PhotoService photoService;
	
	
	public List<String> registerPhoto(PhotoRequestDto photo) {
		List<String> result=new ArrayList<String>();
		
		int len = photo.getPhoto().length;
		for(int i = 0; i < len; i++) {
			// 레시피 사진 이미지 이름 변경
			String fileName = photo.getPhoto()[i].getOriginalFilename();
			String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());  //현재시간
		    String realFileName = now + "_" + fileName;
		    try {
				photo.getPhoto()[i].transferTo(new File(realFileName));
				//insert
				result.add(realFileName);
				
				RecipePhotoDto dto= new RecipePhotoDto();
				dto.setPhotoSrc(realFileName);
				photoService.registerPhoto(photo);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}	
		
		return result;
	}
}
