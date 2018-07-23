package cn.itcast.shop.payment.entity;
/**
 * 订单支付信息实体类
 * @author admin
 *
 */
public class Payment {
	private String Cmd ; // 业务类型:
	private String MerId ;// 商户编号:
	private String Order;// 订单编号:
	private String Amt ; // 付款金额:
	private String Cur ; // 交易币种:
	// 支付成功后跳转的页面路径
	private String Url ; // 商户接收支付成功数据的地址:
	private String FrpId ;// 支付通道编码:
	private String keyValue ; // 秘钥
	
	public String getCmd() {
		return Cmd;
	}
	public void setCmd(String cmd) {
		Cmd = cmd;
	}
	public String getMerId() {
		return MerId;
	}
	public void setMerId(String merId) {
		MerId = merId;
	}
	public String getOrder() {
		return Order;
	}
	public void setOrder(String order) {
		Order = order;
	}
	public String getAmt() {
		return Amt;
	}
	public void setAmt(String amt) {
		Amt = amt;
	}
	public String getCur() {
		return Cur;
	}
	public void setCur(String cur) {
		Cur = cur;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	public String getFrpId() {
		return FrpId;
	}
	public void setFrpId(String frpId) {
		FrpId = frpId;
	}
	public String getKeyValue() {
		return keyValue;
	}
	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}
}
