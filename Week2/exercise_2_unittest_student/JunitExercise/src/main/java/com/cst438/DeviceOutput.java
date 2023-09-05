package com.cst438;

public interface DeviceOutput {
	int pumpOneUnit();  // deliver 1 unit of drug
	void alarm1();  // high alarm
	void alarm2();  // critical low alarm
	void resetAlarms(); 
}
