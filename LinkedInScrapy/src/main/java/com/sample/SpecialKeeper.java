package com.sample;

public class SpecialKeeper extends OutputKeeper {
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		try {
			this.writeToExcelFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
