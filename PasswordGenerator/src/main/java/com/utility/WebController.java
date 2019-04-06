package com.utility;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WebController {
	
	LinkedList<Character> digits, bigs, smalls, specials, all;
		
	public WebController() {
		digits = new LinkedList<Character>();
		bigs = new LinkedList<Character>();
		smalls = new LinkedList<Character>();
		specials = new LinkedList<Character>();
		all = new LinkedList<Character>();
		for (char letter = 32; letter < 127; letter++) {
			all.add(letter);
			if (Character.isDigit(letter)) {
				digits.add(letter);
			} else if (Character.isUpperCase(letter)) {
				bigs.add(letter);
			} else if (Character.isLowerCase(letter)) {
				smalls.add(letter);
			} else {
				specials.add(letter);
			}
		}
	}

	@RequestMapping("/home")
	public ResponseEntity getHome(@RequestHeader HttpHeaders headers) {
		File homepage = new File("index.html");
		String origin = headers.getOrigin();
		Map<String, String> map;
		map = new TreeMap();
		return new ResponseEntity(origin, HttpStatus.OK);
	}
	
	@RequestMapping("/requirements")
	public ResponseEntity<String> createPassword(@RequestParam Map<String, String> params) {

		System.out.println(params.get("length"));
		LinkedList<Character> password = new LinkedList<Character>();
		int length = Integer.valueOf(params.get("length"));
		
		if (params.containsKey("uppercase")) {
			password.add( bigs.get((int) Math.floor(bigs.size() * Math.random())) );
			length--;
		}
		if (params.containsKey("lowercase")) {
			password.add( smalls.get((int) Math.floor(smalls.size() * Math.random())) );
			length--;
		}
		if (params.containsKey("digit")) {
			password.add( digits.get((int) Math.floor(digits.size() * Math.random())) );
			length--;
		}
		if (params.containsKey("special")) {
			password.add( specials.get((int) Math.floor(specials.size() * Math.random())) );
			length--;
		}
		
		for (int i = 0; i < length; i++) {
			double index = Math.floor(all.size() * Math.random());
			char newChar = all.get((int) index);
			password.add(newChar);
		}
		
		String newPassword = "";
		
		while (!password.isEmpty()) {
			newPassword += password.remove( (int) Math.floor(password.size() * Math.random()) );
		}
		
		return new ResponseEntity<String>(newPassword, HttpStatus.OK);
	}
}
