package com.example.demo;

import java.util.ArrayList;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class FBLA_EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public ArrayList<Integer> verificationEmail(String email) {
    	try {
    		ArrayList<Integer> code = new ArrayList<Integer>();
    		Random random = new Random();
    		for(int i = 0; i<6; i++) {
    			code.add(random.nextInt(10));
    		}
    		SimpleMailMessage message = new SimpleMailMessage();
    	    message.setTo(email);
    	    message.setSubject("FBLA Verification Email");
    	    String text = "Your FBLA verification code is: " + code.get(0) + code.get(1) + code.get(2) + code.get(3) + code.get(4) + code.get(5) + "\n\nIf you did not sign up for this service, please disregard this email.";
    	    message.setText(text);
    	    javaMailSender.send(message);
    	    return code;
    	}
		catch(Exception e) {
			e.printStackTrace();
			ArrayList<Integer> error = new ArrayList<Integer>();
			error.add(-1);
			return error;
		}
	}
//    public void reportEmail() {
//    	SimpleMailMessage message = new SimpleMailMessage();
//        String email = FBLAService.getEmail();
//        message.setTo(email);
//        message.setSubject("Finance Report");
//        //message.setText(text);
//        javaMailSender.send(message);
//
//    }
}
