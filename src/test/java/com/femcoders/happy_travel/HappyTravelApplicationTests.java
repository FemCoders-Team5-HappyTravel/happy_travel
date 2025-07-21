package com.femcoders.happy_travel;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class HappyTravelApplicationTests {


	public class PasswordGenerator {
		public static void main(String[] args) {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			System.out.println("BCrypt hash for 'adminpass': " + encoder.encode("adminpass"));
			System.out.println("BCrypt hash for 'password123': " + encoder.encode("password123"));
		}
	}

	@Test
	void contextLoads() {
	}

}
