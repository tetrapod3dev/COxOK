package com.ssafy.cookblog.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.cookblog.dao.MeetDao;
import com.ssafy.cookblog.dto.MeetDto;
import com.ssafy.cookblog.dto.MeetJoinDto;
import com.ssafy.cookblog.dto.request.MeetRegisterRequestDto;
import com.ssafy.cookblog.dto.response.MeetViewResponseDto;

@Service
public class MeetServiceImpl implements MeetService {

	@Autowired
	private MeetDao meetDao;

	@Override
	public List<MeetDto> getAllMeet(int startIndex) {
		return meetDao.selectAllMeet(startIndex);
	}

	@Override
	public MeetViewResponseDto getOneMeet(long meetId) {
		MeetViewResponseDto meetViewResponse = meetDao.selectMeet(meetId);
		meetViewResponse.setMeetJoinList(meetDao.selectMeetJoinList(meetId));
		return meetViewResponse;
	}

	@Override
	@Transactional 
	public int deleteMeet(long meetId) {
		return meetDao.delete(meetId);
	}
	
	@Override
	@Transactional 
	public int register(MeetRegisterRequestDto meetRegisterReqeustDto) {
		
		//썸네일 ec2에 등록
		String fileName= meetRegisterReqeustDto.getPhoto().getOriginalFilename();
		String now = new SimpleDateFormat("yyyyMMddHmsS").format(new Date()); 
		String realFileName=now+"_"+fileName;
		try {
			meetRegisterReqeustDto.getPhoto().transferTo(new File(realFileName));
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//insert용 meetDto 생성
		MeetDto meetDto = new MeetDto();
		meetDto.setUserId(meetRegisterReqeustDto.getUserId());
		meetDto.setRecipeId(meetRegisterReqeustDto.getRecipeId());
		meetDto.setThumbnailSrc(realFileName);
		meetDto.setType(meetRegisterReqeustDto.getType());
		meetDto.setJoinLimit(meetRegisterReqeustDto.getJoinLimit());
		meetDto.setPrice(meetRegisterReqeustDto.getPrice());
		meetDto.setTitle(meetRegisterReqeustDto.getTitle());
		meetDto.setContent(meetRegisterReqeustDto.getContent());
		meetDto.setAddress(meetRegisterReqeustDto.getAddress());
		meetDto.setLat(meetRegisterReqeustDto.getLat());
		meetDto.setLng(meetRegisterReqeustDto.getLng());
		meetDto.setDate(meetRegisterReqeustDto.getDate());

		//insert
		return meetDao.insert(meetDto);
	}
	
	@Override
	@Transactional 
	public int modify(MeetDto meetDto) {
		return meetDao.update(meetDto);
	}
	
	@Override
	@Transactional 
	public int registerMeetJoin(MeetJoinDto meetJoinDto) {
		return meetDao.insertMeetJoin(meetJoinDto);
	}
	
	@Override
	@Transactional 
	public int removeMeetJoin(MeetJoinDto meetJoinDto) {
		return meetDao.deleteMeetJoin(meetJoinDto);
	}
	
	@Override
	public List<MeetDto> getAllMeetByType(MeetDto meetDto) {
		return meetDao.selectMeetByType(meetDto);
	}
	
}
