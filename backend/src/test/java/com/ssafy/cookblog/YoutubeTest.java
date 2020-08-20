package com.ssafy.cookblog;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ssafy.cookblog.dto.request.YoutubeRegisterRequestDto;
import com.ssafy.cookblog.service.YoutubeService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YoutubeTest {

	@Autowired
	YoutubeService YoutubeService;
	
	@Test
	public void RecipeTest() {
		YoutubeRegisterRequestDto dto =new YoutubeRegisterRequestDto();
		dto.setRecipeId(71);
		List<String> a=new ArrayList<String>();
		a.add("1");
		a.add("3");
		a.add("2");
		dto.setThumbnailSrc(a);
		dto.setVideoId(a);
		YoutubeService.register(dto);
	}
}
