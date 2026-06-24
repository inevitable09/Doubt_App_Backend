package com.rudra.Doubt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.mail.internet.MimeMessage;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

@Service
public class DoubtService {

	@Autowired
	JavaMailSender jms;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	RestTemplate restTemplate;

	public ObjectNode se(String name, String phone, String email, String doubt, MultipartFile file) {
		try {
			MimeMessage mm = jms.createMimeMessage();
			MimeMessageHelper mmh = new MimeMessageHelper(mm, true);

			String to = "rudrarothe9@gmail.com";
			String subject = "New Enquiry from " + name;
			String text = "Name = " + name + "\nPhone = " + phone + "\nEmail = " + email + "\nDoubt = " + doubt;

			mmh.setTo(to);
			mmh.setSubject(subject);
			mmh.setText(text);

			if (! file.isEmpty()) {
				mmh.addAttachment(file.getOriginalFilename(), file);
			}

			jms.send(mm);
			
			ObjectNode res = objectMapper.createObjectNode();
			res.put("status", "success");
			return res;
		} catch(Exception e) {
			ObjectNode err = objectMapper.createObjectNode();
			err.put("status", "failed");
			err.put("error", e.getMessage());
			return err;
		}
	}

}
