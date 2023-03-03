package com.digitexx.main;

import com.digitexx.config.Configuration;
import com.digitexx.scheduler.MainSheduler;




public class Action {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Configuration.configDB();
		MainSheduler.start();
		

	}

}
