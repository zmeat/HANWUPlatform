package com.application;

import org.apache.log4j.Logger;

public class Bootstrap {
	private static Logger log = Logger.getLogger(Bootstrap.class);
	public static void main(String[] args) throws ClassNotFoundException {
		log.debug("the bootsrap is begin");
		Container container = new Container();
		
//		System.out.println(container.getService(container.getDBPath()));
	}
}

