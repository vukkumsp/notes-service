package com.vukkumsp.notes_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ImportAutoConfiguration(exclude = {
		org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration.class
})
class NotesServiceApplicationTests {

//	@Test
//	void contextLoads() {
//	}

}
