package com.markbucciarelli.hotwiredemochat;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
class HotwireDemoChatApplicationTests {

	@Test
	// No need for an assert---if the context doesn't load this test fails.
	@SuppressWarnings("java:S2699")
	void contextLoads() {
	}


	@Test
	@Sql({"/schema-h2.sql"})
	// No need for an assert---if the SQL schema is invalid, this test fails.
	@SuppressWarnings("java:S2699")
	void testH2Schema() {
	}

}
