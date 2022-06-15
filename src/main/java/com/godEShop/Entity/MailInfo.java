package com.godEShop.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
    private String from;
    private String to;
    private String[] cc;
    private String[] bcc;
    private String subject;
    private String body;
    private String[] attachments;
//	private MultipartFile[] attachments;

    public MailInfo(String to, String subject, String body) {
	this.from = "GodWatch (GodEdoc Team)";
	this.to = to;
	this.subject = subject;
	this.body = body;
    }
}
