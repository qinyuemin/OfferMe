package com.offerme.intf.send;

/**
 * 消息回调接口
 * 
 */
public interface ICallBack
{
	/**
	 * 设置消息发送结果
	 * 
	 * @param message
	 */
	public void setSendResult(IMessage message) throws Exception;
}
