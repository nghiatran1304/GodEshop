package com.godEShop.Service.Impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.godEShop.Entity.MailInfo;
import com.godEShop.Service.MailerService;

@Service
public class MailerServiceImpl implements MailerService {
    @Autowired
    JavaMailSender sender;

    @Override
    public void send(MailInfo mail) throws MessagingException {
	// TODO Auto-generated method stub
	// tạo message
	MimeMessage message = sender.createMimeMessage();

	// dùng helper thiết tập các thông tin
	MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
	helper.setFrom(mail.getFrom());
	helper.setTo(mail.getTo());
	helper.setSubject(mail.getSubject());
	helper.setText(mail.getBody(), true);
	helper.setReplyTo(mail.getFrom());

	String[] cc = mail.getCc();
	if (cc != null && cc.length > 0) {
	    helper.setCc(cc);
	}
	String[] bcc = mail.getBcc();
	if (bcc != null && bcc.length > 0) {
	    helper.setBcc(bcc);
	}
	String[] attachments = mail.getAttachments();
	if (attachments != null && attachments.length > 0) {
	    for (String path : attachments) {
		File file = new File(path);
		helper.addAttachment(file.getName(), file);
	    }
	}
	// Gửi message đến SMTP server
	sender.send(message);

    }

    @Override
    public void send(String to, String subject, String body) throws MessagingException {
	// TODO Auto-generated method stub
	this.send(new MailInfo(to, subject, body));
    }

    List<MailInfo> list = new ArrayList<>();

    @Override
    public void queue(MailInfo mail) {
	list.add(mail);
    }

    @Override
    public void queue(String to, String subject, String body) {
	queue(new MailInfo(to, subject, body));
    }

    // cron = "0 * * * * MON-FRI" : giây đầu tiên của mỗi phút từ thứ 2 đến thứ 6
    // fixedRate = 3000 : Thời gian giữa các hoạt động là 3 giây
    // fixedDelay = 5000 : Thời gian chờ hoạt động tiếp theo là 5 giây
    // initialDelay = 5000 : sau khi khởi động 5 giây thì bắt đầu
    // https://www.freeformatter.com/cron-expression-generator-quartz.html
    @Scheduled(fixedDelay = 5000)
    public void run() {
	while (!list.isEmpty()) {
	    MailInfo mail = list.remove(0);
	    try {
		this.send(mail);
		System.out.println("Send email OK");
	    } catch (Exception e) {
		e.printStackTrace();
		System.out.println("Send email FAILED");
	    }
	}
    }
}
