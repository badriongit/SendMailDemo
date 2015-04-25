package demo;

import java.io.File;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SendMailController {

	private final String recipient = "badrionlion@gmail.com";

	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSenderImpl sender;

	@Autowired
	private SimpleMailMessage templateMessage;

	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setTemplateMessage(SimpleMailMessage templateMessage) {
		this.templateMessage = templateMessage;
	}

	public void setMailSenderImpl(JavaMailSenderImpl sender) {
		this.sender = sender;
	}

	@RequestMapping(value = "/mimeMail", method = RequestMethod.GET)
	public @ResponseBody String sendMIMEMail(HttpServletResponse response) {
		try {
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			helper.setTo(recipient);
			helper.setSubject("Spring : Test Mail(MIME)");
			helper.setText(
					"Dear Badri,<br><br> This is a MIME mail with an attachment sent from Spring Mail Framework "
					+ "done using this "
					+ "<a href=\"http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html\">"
					+ "tutorial</a>..<br><br>Regards,<br>Badri", true);
			FileSystemResource file1 = new FileSystemResource(new File(
					"/Users/badrionapple/Desktop/me_2011.jpg"));
			 helper.addAttachment("me_2011.jpg", file1);
			sender.send(message);
			return "Success !! MIME Mail sent to " + recipient + "!!";
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return "Failure !! MIME Mail not sent to " + recipient + "!!";
		}
	}

	@RequestMapping(value = "/textMail", method = RequestMethod.GET)
	public @ResponseBody String sendTextMail(HttpServletResponse response) {
		try {
			SimpleMailMessage msg = new SimpleMailMessage(this.templateMessage);
			msg.setTo(recipient);
			msg.setText("Dear Badri, \n \n This is a simple text mail sent from Spring Mail Framework done using this tutorial(http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mail.html)"
					+ "\n \nRegards,\nBadri");
			this.mailSender.send(msg);
			System.out.println("Success !! Text Mail Sent !! ");
			return "Success !! Text Mail sent to " + recipient + " !!";
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			return "Failure !! Text Mail not sent to " + recipient + " !!";
		}
	}

}
