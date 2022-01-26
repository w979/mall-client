package com.wry.utils;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件工具类
 * @author luchengbo
 *
 */
public class EmailUtil {
    
	/**
	 * 发送邮件
	 * @param eamil 收件人邮箱地址
	 */
	public static boolean sendEmail(String eamil,String code) {
		try {
			/**
			 * 配置发件人信息
			 */
			//1、创建连接邮件服务器的参数配置
			Properties props = new Properties();
			//设置发件人的邮箱名
			props.put("mail.user","1430018266@qq.com");
			//设置发件人授权码
			props.put("mail.password","dgwswnhkgkelffic");
			//设置发件人的用户的认证方式
			props.put("mail.smtp.auth","true");
			//设置发件人传输协议
			props.put("mail.transport.protocal","smtp");
			//设置发件人的SMTP服务器地址
			props.put("mail.smtp.host","smtp.qq.com");
			//设置发件人邮箱端口
			props.put("mail.smtp.prot","25");
			
			//2、创建定义整个应用程序所需的环境信息的 Session 对象
			Session mailSession = Session.getDefaultInstance(props);
			//3、创建邮件的实例对象
			Message msg = new MimeMessage(mailSession);
			//设置发件人的邮箱地址
			msg.setFrom(new InternetAddress("1430018266@qq.com"));
			//设置收件人的邮件地址
			msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(eamil));//收件人的邮箱地址
			//设置发送邮件标题
			msg.setSubject("有新的验证码啦");
			//设置发送邮件内容
			msg.setContent("<h2>尊敬的——【"+eamil+"】,</h2><h3>你的验证码为:"+code+",两分钟内有效！</h3>","text/html;charset=UTF-8");
			msg.saveChanges();
			
			//4、根据mailSession对象获取邮件传输对象Transport
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(props.getProperty("mail.smtp.host"),props.getProperty("mail.user"),props.getProperty("mail.password"));
			//发送邮件
			transport.sendMessage(msg,msg.getAllRecipients());
			transport.close();
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
