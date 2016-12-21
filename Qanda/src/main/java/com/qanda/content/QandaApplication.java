package com.qanda.content;

import com.avos.avoscloud.AVCloud;
import com.avos.avoscloud.AVOSCloud;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QandaApplication {
	public static void main(String[] args) {
		AVOSCloud.initialize("REDbM7qwoObldj9PrOcnb8CH-gzGzoHsz", "0W9OdlaXbliacEJRdt6qVo2m", "fFkVAsWm4yj2Ko3NUH0Qdsxe");
		AVOSCloud.setShouldUseMasterKey(true);
		SpringApplication.run(QandaApplication.class, args);
	}
}
