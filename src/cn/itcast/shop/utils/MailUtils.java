package cn.itcast.shop.utils;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件发送工具类
 * @author admin
 *
 */
public class MailUtils {
	/**
	 * 发送邮件的方法
	 * @param to	:收件人邮箱
	 * @param code	:激活码
	 */
	public static void sendMail(String to, String code) {
		 /*
		  * 1.获得一个Session对象
		  * 2.创建一个代表邮件的对象:Message
		  * 3.使用Transport对象发送邮件
		  */
//		1.创建连接对象,导mail包下的Session
        Properties props = new Properties();
//      设置发送邮件的主机
        props.setProperty("mail.host", "localhost");
//      不能直接new, 要使用 getInstance(Properties props, Authenticator authenticator)
//      authenticator:根据账户密码认证才能连接
		Session session = Session.getInstance(props, new Authenticator(){
//			new一个接口,在接口里实现账户连接邮箱服务器
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("service@shop.com", "111");
			}
			
		});
		
//		2.创建邮件对象
		Message message = new MimeMessage(session);
        try {
//          设置发件人
            message.setFrom(new InternetAddress("service@shop.com"));
//          增加收件人
//          addRecipient(RecipientType type 发送类型, Address address 接收方地址)
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
//			设置标题
			message.setSubject("这是一封商城的激活邮件!");
//			设置邮箱的正文 setContent(Object arg0 正文内容, String arg1 格式)
//			 格式: 纯文本-->text/plain 超链接-->text/html
			message.setContent(
                    "<div style='background-color:#ECECEC; padding: 35px;'>"
                  + "<table cellpadding='0' align='center'style='width: 600px; margin: 0px auto; text-align: left; position: relative; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 5px; border-bottom-left-radius: 5px; font-size: 14px; font-family:微软雅黑, 黑体; line-height: 1.5; box-shadow: rgb(153, 153, 153) 0px 0px 5px; border-collapse: collapse; background-position: initial initial; background-repeat: initial initial;background:#fff;'>"
                  + "<tbody>"
                  + " <tr>"
                  + "     <th valign='middle' style='height: 25px; line-height: 25px; padding: 15px 35px; border-bottom-width: 1px; border-bottom-style: solid; border-bottom-color: #C46200; background-color: #FEA138; border-top-left-radius: 5px; border-top-right-radius: 5px; border-bottom-right-radius: 0px; border-bottom-left-radius: 0px;'>"
                  + "         <font face='微软雅黑' size='5' style='color: rgb(255, 255, 255); '>HEllO!（传智商城）</font></th>"
                  + " </tr> "
                  + " <tr> "
                  + "     <td>"
                  + "         <div style='padding:25px 35px 40px; background-color:#fff;'>"
                  + "                 <p>购物天堂官方激活邮件!<br>"
                  + "             你只需要点击下面的链接即可激活您的账户:<br>"
                  + "             <a href='http://localhost:8080/Shop/user_active.action?code="+ code + "'>http://localhost:8080/shop/user_active.action?code="+ code + "</a></p>"
                  + "             <p align='right'>传智商城官方团队</p>"
                  + "         </div></td>" + "    </tr>" + "</tbody>"
                  + "</table>" + "</div>", 
                  "text/html;charset=UTF-8");
//			3.使用Transport对象发送邮件
			Transport.send(message);
			
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
//    简单测试的main函数
//	public static void main(String[] args) {
//		sendMail("aaa@shop.com", "12345678900987654321");
//	}
}
