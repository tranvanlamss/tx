package com.vietsoft.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.vietsoft.common.Constants;
import com.vietsoft.config.ServerProperties;
import com.vietsoft.model.User;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class MailSenderService {

	private static final String EMAIL_VERIFYING_REQ_TMPL = "/templates/mails/email-verifying-req.txt";
	private static final String RESET_PASSWD_REQ_TMPL = "/templates/mails/reset-passwd-req.txt";
	private static final String INFOM_NEW_PASSWD_REQ_TMPL = "/templates/mails/infom-new-passwd.txt";
	private static final String CHANGE_PASSWD_TMPL = "/templates/mails/changed-passwd.txt";
	private static final String WELCOME_NEW_MEMBER_TMPL = "/templates/mails/welcome-new-member.txt";
	private static final String CONFIRM_ACTIVATED_WARRANTY_TMPL = "/templates/mails/confirm-warranty-success.txt";

	

	@Value("${spring.mail.default-from}")
	String defaultFrom;

	@Autowired
	JavaMailSender sender;

	@Autowired
	private ServerProperties props;

	public void send(String to, String subject, String content) throws MessagingException {
		this.send(defaultFrom, to, subject, content);
	}

	public void send(String from, String to, String subject, String content) throws MessagingException {
		this.send(from, to, null, null, subject, content);
	}

	public void send(String from, String to, String subject, String content, String fileAttack)
			throws MessagingException {
		this.send(from, to, null, null, subject, content, fileAttack);
	}

	public void send(String from, String to, String cc, String bcc, String subject, String content)
			throws MessagingException {

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom((from == null || from.isEmpty()) ? defaultFrom : from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content);
		helper.setText(content, true);
		if (cc != null) {
			helper.setCc(cc);
		}
		if (bcc != null) {
			helper.setBcc(bcc);
		}

		// helper.addAttachment("attachment-document-name.jpg", new
		// ClassPathResource("memorynotfound-logo.jpg"));

		sender.send(message);

	}

	public void send(String from, String to, String cc, String bcc, String subject, String content, String fileUrl)
			throws MessagingException {

		boolean isHtmlContent = true;

		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom((from == null || from.isEmpty()) ? defaultFrom : from);
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(content);
		helper.setText(content, isHtmlContent);
		FileSystemResource res = new FileSystemResource(new File(fileUrl));
		helper.addAttachment("Invoice.pdf", res);
		if (cc != null) {
			helper.setCc(cc);
		}
		if (bcc != null) {
			helper.setBcc(bcc);
		}

		// helper.addAttachment("attachment-document-name.jpg", new
		// ClassPathResource("memorynotfound-logo.jpg"));

		sender.send(message);

	}

	private String loadEmailTemplate(String filepath) throws IOException {
		InputStream ins = getClass().getResourceAsStream(filepath);
		return IOUtils.toString(ins, "UTF-8");
	}

	public void sendVerifyingReq(User user)
			throws IOException, MessagingException {
		Map<String, String> query = new HashMap<String, String>();
		query.put("uid", String.valueOf(user.getId()));
		query.put("token", user.getEmailVerifiedToken());
		String verifyHref = "";
		try {
			verifyHref = props.getURIPath(Constants.PATH_ANGULAR_CONFIRM_EMAIL, query);
		} catch (URISyntaxException e1) {
//			e1.printStackTrace();
		}

		String fullName = user.getFullName();
		if (StringUtils.isEmpty(fullName)) {
			fullName = user.getLastName() + " " + user.getFirstName();
		}
		String content = loadEmailTemplate(EMAIL_VERIFYING_REQ_TMPL)
				.replaceAll("(\\$\\{userName\\})", fullName)
				.replaceAll("(\\$\\{verifyHref\\})", verifyHref);
		send(defaultFrom,
				user.getEmail(),
				"nghiem.van.viet@vsi-international.com",
				"nghiem.van.viet@vsi-international.com",
				Constants.EMAIL_SUBJECT_VERIFY_EMAIL_REQUEST,
				content);
	}

	public void sendResetPasswordReq(User user, String token)
			throws IOException, MessagingException {
		Map<String, String> query = new HashMap<>();
		query.put("uid", String.valueOf(user.getId()));
		query.put("token", token);

		String verifyHref = "";
		try {
			verifyHref = props.getURIPath(Constants.PATH_ANGULAR_RESET_PASSWORD, query);
		} catch (URISyntaxException e1) {

		}

		String fullName = user.getFullName();
		if (StringUtils.isEmpty(fullName)) {
			fullName = user.getLastName() + " " + user.getFirstName();
		}
		String content = loadEmailTemplate(RESET_PASSWD_REQ_TMPL)
				.replaceAll("(\\$\\{userName\\})", fullName)
				.replaceAll("(\\$\\{verifyHref\\})", verifyHref);
		send(user.getEmail(), Constants.EMAIL_SUBJECT_PASSWORD_RESET_REQUEST, content);
	}

	public void sendResetPassword(String to, String subject, String username, String newPass)
			throws IOException, MessagingException {
		String content = loadEmailTemplate(INFOM_NEW_PASSWD_REQ_TMPL).replaceAll("(\\$\\{userName\\})", username)
				.replaceAll("(\\$\\{password\\})", newPass);
		send(to, subject, content);
	}

	public void sendChangedPasswordAck(String to, String subject, String username)
			throws IOException, MessagingException {
		String content = loadEmailTemplate(CHANGE_PASSWD_TMPL).replaceAll("(\\$\\{userName\\})", username);
		send(to, subject, content);
	}

	public void sendWelcomeNewMember(String to, String subject, String username, String newPass)
			throws IOException, MessagingException {
		String content = loadEmailTemplate(WELCOME_NEW_MEMBER_TMPL).replaceAll("(\\$\\{userName\\})", username)
				.replaceAll("(\\$\\{password\\})", newPass);
		send(to, subject, content);
	}
	public void sendEmailConfirmActivateWarranty(String to, String subject, String name, String productionName, String productInfoHref, String dateFrom, String dateTo)
			throws IOException, MessagingException {
		String custName=name;
		if (StringUtils.isEmpty(custName)) custName = "";
		String content = loadEmailTemplate(CONFIRM_ACTIVATED_WARRANTY_TMPL)
				.replaceAll("(\\$\\{custName\\})", custName)
				.replaceAll("(\\$\\{productionName\\})", productionName)
				.replaceAll("(\\$\\{productInfoHref\\})", productInfoHref)
				.replaceAll("(\\$\\{dateFrom\\})", dateFrom)
				.replaceAll("(\\$\\{dateTo\\})", dateTo);
		send(to, subject, content);
	}
	
}
