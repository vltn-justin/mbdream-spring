package com.chamalo.mbdream.controllers;

import com.chamalo.mbdream.services.MotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class MotoControllerTest {

	@MockBean
	private MotoService service;

	@Autowired
	private MotoController controller;
}
