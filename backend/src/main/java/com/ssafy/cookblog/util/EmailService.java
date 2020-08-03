package com.ssafy.cookblog.util;

public interface EmailService {
	public void sendSimpleMessage(String to, String subject,String text);
}
