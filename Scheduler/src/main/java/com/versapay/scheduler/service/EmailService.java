package com.versapay.scheduler.service;

import static org.springframework.util.StringUtils.isEmpty;

import java.io.IOException;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailService implements Job {

	@SuppressWarnings("unused")
	private void sendEmail(JobDataMap map) {
		try {
			String to = map.getString("to");
			String cc = map.getString("cc");
			String bcc = map.getString("bcc");
			String subject = map.getString("subject");
			String messageBody = map.getString("messageBody");

			String from = "tringuyen@futurify.vn";

			Email eFrom = new Email(from);
			Email eTo = new Email(to);
			Content content = new Content("text/html", messageBody);
			Mail mail = new Mail(eFrom, subject, eTo, content);

			String skey = System.getenv("SENDGRID_API_KEY");
			if (isEmpty(skey)) {
				return;
			}

			Request req = new Request();
			req.setMethod(Method.POST);
			req.setEndpoint("mail/send");
			req.setBody(mail.build());
			System.out.println(req.getBody());

			SendGrid sg = new SendGrid(skey);
			Response rsp = sg.api(req);

			System.out.println(rsp.getStatusCode());
			System.out.println(rsp.getBody());
			System.out.println(rsp.getHeaders());
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Job triggered to send emails");
		JobDataMap map = context.getMergedJobDataMap();
		sendEmail(map);
		log.info("Job completed");
	}

}