package demo;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
@ComponentScan("demo")
@PropertySource(value = "classpath:application.properties")
public class SendMailConfig {
	
	@Value("${mail.host}")
	private String host;
	
	@Value("${mail.user}")
	private String user;	
	
	@Value("${mail.password}")
	private String password;	

	@Value("${mail.port}")
	private int port;	

	@Value("${mail.tlsenable}")
	private String tlsEnable;	

	@Value("${mail.tlsboolean}")
	private String tlstrue;	
	
	@Bean
	public JavaMailSenderImpl mailSender(){
		JavaMailSenderImpl mSender = new JavaMailSenderImpl();
		mSender.setHost(host);
		mSender.setPort(port);
		mSender.setJavaMailProperties(getProperties());
		mSender.setUsername(user);
		mSender.setPassword(password);
		return mSender;
	}
	
	private Properties getProperties(){
		Properties javaMailProperties = new Properties();
		javaMailProperties.setProperty(tlsEnable, tlstrue);
		return javaMailProperties;
	}
	
	@Bean
	public SimpleMailMessage templateMessage(){
		SimpleMailMessage templateMsg = new SimpleMailMessage();
		templateMsg.setFrom(user);
		templateMsg.setSubject("Spring : Test Mail(Text)");
		return templateMsg;
	}

}
