package com.mausoft.inv.mgr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mausoft.common.model.IAjaxResponse;
import com.mausoft.common.model.SuccessAjaxResponse;
import com.mausoft.inv.mgr.entity.User;
import com.mausoft.inv.mgr.service.IProfileService;

@RestController
@RequestMapping(path="/profile", produces= MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
	
	@Autowired
	private IProfileService profileService;
	
	@PostMapping(consumes= {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<IAjaxResponse> register(@RequestBody User user) throws Exception {
		return ResponseEntity.ok(new SuccessAjaxResponse("results", profileService.register(user)));
	}
	
	@GetMapping(path="/{username}/exists")
	public ResponseEntity<IAjaxResponse> checkExisting(@PathVariable String username){
		return ResponseEntity.ok(new SuccessAjaxResponse("results", profileService.checkExisting(username)));
	}
}