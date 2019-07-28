package com.linkedin.learning.linkedinlearningfullstackappangularspringboot;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured;
import org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkedInLearningFullStackAppAngularSpringBootApplication.class,
webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationResourceTest {

	@LocalServerPort
	private int port;
	
	@Before
	public void setUp() throws Exception {
		RestAssured.port = Integer.valueOf(port);
		
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}

}
