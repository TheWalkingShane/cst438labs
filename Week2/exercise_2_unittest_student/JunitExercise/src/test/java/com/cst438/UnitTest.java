package com.cst438;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class UnitTest {
	
	@MockBean
	DeviceReader devReader;
	
	@MockBean
	DeviceOutput devOutput;
	
	InsulinPump ip;
	
	@BeforeEach
	public void setup() {
		ip = new InsulinPump(devReader, devOutput);
	}
	
	@Test
	public void testHiReading() {
		long time = 1689881075174L; // 12:24 July 20, 2023
		given(devReader.getGlucoseLevel()).willReturn(140);
		ip.check(time);
		verify(devOutput, times(1)).pumpOneUnit();
		String[] log = ip.getLog(0, 1);
		assertThat(log.length).isEqualTo(1);
		assertThat(log[0]).contains("12:24");
		assertThat(log[0]).contains("140");
	}

	@Test
	public void testNormalReading(){

	}

}
