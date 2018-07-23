 <%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
 <%@ taglib uri="/struts-tags" prefix="s"%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <!-- saved from url=(0043)http://localhost:8080/mango/cart/list.jhtml -->
 <html xmlns="http://www.w3.org/1999/xhtml">
 <head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
 
 <title>支付信息</title>
 </head>
 <body>
 	<div><h3>业务类型Cmd : <s:property value="payment.Cmd"/></h3></div> 
 	<div><h3>商户编号MerId : <s:property value="payment.MerId"/></h3></div> 
 	<div><font color="red"><h3>订单编号Order : <s:property value="payment.Order"/></h3></font></div> 
 	<div><h3>付款金额Amt : <s:property value="payment.Amt"/></h3></div> 
 	<div><h3>支付通道编码FrpId : 
	 	<s:if test="payment.FrpId == 'ICBC-NET-B2C' ">
	 		中国银行
	 	</s:if>
	 	<s:elseif test="payment.Amt == 'BOC-NET-B2C' ">
	 		工商银行 
	 	</s:elseif>
	 	<s:elseif test="payment.Amt == 'ABC-NET-B2C' ">
	 		农业银行 
	 	</s:elseif>
	 	<s:elseif test="payment.Amt == 'BOCO-NET-B2C' ">
	 		交通银行
	 	</s:elseif>
	 	<s:elseif test="payment.Amt == 'PINGANBANK-NET' ">
	 		平安银行 
	 	</s:elseif>
	 	<s:elseif test="payment.Amt == 'CCB-NET-B2C' ">
	 		建设银行
	 	</s:elseif>
	 	<s:elseif test="payment.Amt == 'CEB-NET-B2C' ">
	 		光大银行
	 	</s:elseif>
	 	<s:elseif test="payment.Amt == 'CMBCHINA-NET-B2C' ">
	 		招商银行
	 	</s:elseif>
 	</h3></div>
 	<a href="${pageContext.request.contextPath}/order_callBack.action?Order=<s:property value="payment.Order"/>&Amt=<s:property value="payment.Amt"/>"><font color="red">模拟支付成功</font></a>
 </body>
 </html>