package com.yunkai.browser.scan;

public class MessageType {

	public interface BaiscMessage{
		
		int BASE = 0x00000010;
		
		int SEVICE_BIND_SUCCESS = BASE + 1;
		int SEVICE_BIND_FAIL = BASE + 2;
		int DETECT_PRINTER_SUCCESS = BASE + 5;
		int SCAN_RESULT_GET_SUCCESS = BASE + 7;
		
	}

	
}
