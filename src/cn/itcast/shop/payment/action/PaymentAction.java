package cn.itcast.shop.payment.action;

import cn.itcast.shop.payment.entity.Payment;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 订单支付信息
 * @author admin
 *
 */
public class PaymentAction extends ActionSupport {

	// 接收参数
	
	private String Cmd ; // 业务类型:
	private String MerId ;// 商户编号:
	private String Order;// 订单编号:
	private String Amt ; // 付款金额:
	private String FrpId ;// 支付通道编码:
	
	public void setCmd(String cmd) {
		Cmd = cmd;
	}
	public void setMerId(String merId) {
		MerId = merId;
	}
	public void setOrder(String order) {
		Order = order;
	}
	public void setAmt(String amt) {
		Amt = amt;
	}
	public void setFrpId(String frpId) {
		FrpId = frpId;
	}
	
	// 显示提交过来的Action
	public String sendInfo(){
		Payment payment = new Payment();
		payment.setAmt(Amt);
		payment.setCmd(Cmd);
		payment.setFrpId(FrpId);
		payment.setMerId(MerId);
		payment.setOrder(Order);
		// 通过值栈将分页数据显示到页面上
		ActionContext.getContext().getValueStack().set("payment", payment);
		return "showInfo";
	}

}
