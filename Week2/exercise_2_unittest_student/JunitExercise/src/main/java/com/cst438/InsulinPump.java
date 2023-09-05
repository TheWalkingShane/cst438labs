package com.cst438;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InsulinPump {
	private static final int HI_LEVEL = 130;
	private static final int LO_LEVEL = 70;
	private static final int CRITICAL_HI = 220;
	private static final int MAX_LOG_SIZE = 2000;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm");
	
	private ArrayList<String> log = new ArrayList<>();
	private DeviceReader rdr;
	private DeviceOutput out;
	
	public InsulinPump(DeviceReader rdr, DeviceOutput out) {
		this.rdr = rdr;
		this.out = out;		
	}
	public synchronized void check(long timeMillis) {
		int glucoseLevel = rdr.getGlucoseLevel();
		String logEntry = sdf.format(new Date(timeMillis)) + " Glucose Level: " + glucoseLevel;

		if (glucoseLevel >= CRITICAL_HI) {
			out.alarm2();
			out.pumpOneUnit();
			logEntry += " - Critical High!";
		} else if (glucoseLevel >= HI_LEVEL) {
			out.pumpOneUnit();
			logEntry += " - High.";
		} else if (glucoseLevel <= LO_LEVEL) {
			out.alarm1();
			logEntry += " - Low!";
		} else {
			logEntry += " - Normal.";
		}

		log.add(logEntry);
		if (log.size() > MAX_LOG_SIZE) {
			log.remove(0);
		}
	}

	public synchronized String[] getLog(int skip, int limit) {
		skip = Math.max(0, skip);
		limit = Math.min(log.size() - skip, limit);

		String[] result = new String[limit];
		for (int i = 0; i < limit; i++) {
			result[i] = log.get(skip + i);
		}
		return result;
	}

}
