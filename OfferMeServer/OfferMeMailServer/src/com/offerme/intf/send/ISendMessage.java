package com.offerme.intf.send;

/**
 * 消息管理接口
 * 
 */
public interface ISendMessage
{
	/**
	 * 初始化接口，实现类根据传入键值对进行初始化
	 * @return
	 */
	public ISendMessage getSendMessage() throws Exception;

	/**
	 * 发送消息接口
	 * @param notify
	 * @return
	 * @throws Exception
	 */
	public void send(IUser user) throws Exception;

	/**
	 * 接收消息接口
	 * @param msgID
	 * @return
	 * @throws Exception
	 */
	public IMessage receive(Object msgID) throws Exception;

	/**
	 * 回调接口
	 * 
	 * @param callBack 传入回调接口
	 * @throws Exception
	 */
	public void setCallBack(ICallBack callBack) throws Exception;

}
