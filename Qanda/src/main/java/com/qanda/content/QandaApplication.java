package com.qanda.content;

import com.avos.avoscloud.AVOSCloud;
import com.qanda.content.dao.UserDAO;
import com.qanda.content.dao.UserDAOImp;
import com.qanda.content.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QandaApplication {
	public static void main(String[] args) {
		AVOSCloud.initialize("REDbM7qwoObldj9PrOcnb8CH-gzGzoHsz", "0W9OdlaXbliacEJRdt6qVo2m", "fFkVAsWm4yj2Ko3NUH0Qdsxe");
		SpringApplication.run(QandaApplication.class, args);
	}
}
