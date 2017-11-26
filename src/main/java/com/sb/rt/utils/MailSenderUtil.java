package com.sb.rt.utils;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author ankur
 * 
 */
public class MailSenderUtil {

	@Autowired
	private MailSender mailSender;

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${java.mail.application.name}")
	private String applicationName;

	@Value("${java.mail.user}")
	private String applicationEmail;

	/**
	 * This method is used to set all the attributes for SimpleMailMessage to
	 * invoke the MailSender's send method.
	 * 
	 * @param from
	 * @param to
	 * @param subject
	 * @param message
	 */
	public void sendMail(String from, String to, String subject, String message) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();

		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);

		mailSender.send(mailMessage);
	}

	/**
	 * This method is used to send the email.
	 * 
	 * @param to
	 * @param subject
	 * @param message
	 */
	public void sendMail(String to, String subject, String message) {

		MimeMessage mimeMessage = null;
		try {
			InternetAddress from = new InternetAddress(applicationEmail, applicationName);
			mimeMessage = javaMailSender.createMimeMessage();
			mimeMessage.setSubject(subject);
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setFrom(from);
			helper.setTo(to);
			helper.setText(message, true);
		}
		catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		catch (MessagingException e) {
			e.printStackTrace();
		}
		javaMailSender.send(mimeMessage);
	}

	/**
	 * @return the mailSender
	 */
	public MailSender getMailSender() {
		return mailSender;
	}

	/**
	 * @param mailSender
	 *            the mailSender to set
	 */
	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

}
