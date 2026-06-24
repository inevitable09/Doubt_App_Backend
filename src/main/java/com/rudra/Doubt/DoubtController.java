package com.rudra.Doubt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tools.jackson.databind.node.ObjectNode;
import tools.jackson.databind.ObjectMapper;

@RestController
@CrossOrigin(origins="*")
public class DoubtController {
	@Autowired
	DoubtService service;

	@PostMapping("/se")
	public ObjectNode se(@RequestParam String name, @RequestParam String phone, @RequestParam String email, @RequestParam String doubt, @RequestParam MultipartFile file) {
		ObjectNode res = new ObjectMapper().createObjectNode();
		if (file.getSize() > 5 * 1024 * 1024) {
			res.put("status", "failed");
			res.put("error", "File too large! Max allowed is 5MB.");
			return res;
		}
		return service.se(name, phone, email, doubt, file);
	}
}
