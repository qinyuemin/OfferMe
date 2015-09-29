package com.offerme.send.sms;

import java.util.ArrayList;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.log4j.Logger;

import com.offerme.intf.send.ICallBack;
import com.offerme.intf.send.IMessage;
import com.offerme.intf.send.ISendMessage;
import com.offerme.intf.send.IUser;


public class SMSManage implements ISendMessage
{
	public static Logger myLog = Logger.getLogger("Send");
	
	// 单例模式短信管理
	private static SMSManage sms = null;
	// 短信设置
	private static SMSSetting sms_set = null;
	// 回调接口
	private ICallBack backResult = null;
	
	/**
	 * 获取短信管理
	 * @return
	 * @throws Exception
	 */
	public ISendMessage getSendMessage() throws Exception
	{
		if(sms == null)
		{
			sms = new SMSManage();
			sms_set = new SMSSetting();
		}
		return sms;
	}

	/**
	 * 发送信息
	 */
	public void send(IUser user) throws Exception
	{
		SMS sms = new SMS();
		sms.load(user);
		try
		{
			Call call = (Call) new Service().createCall();
			// 设置WebService地址
			call.setTargetEndpointAddress(sms_set.getProperty(SMSSetting.Setting.WebServiceAddress));
			// 设置调用方法名称
			call.setOperationName(sms_set.getProperty(SMSSetting.Setting.OperationName));
			// 设置用户名及密码
			if ("".equals(sms_set.getProperty(SMSSetting.Setting.UserName)) == false)
			{
				call.setUsername(sms_set.getProperty(SMSSetting.Setting.UserName));
				call.setPassword(sms_set.getProperty(SMSSetting.Setting.Password));
			}
			// 设置传入参数样式及类型
			call.addParameter("username", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("title", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("content", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("date", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("receiver", org.apache.axis.encoding.XMLType.SOAP_ARRAY, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("mobile", org.apache.axis.encoding.XMLType.SOAP_ARRAY, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("platform", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			call.addParameter("link", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
			// 设置返回值样式及类型
			call.setReturnType(org.apache.axis.encoding.XMLType.XSD_INT);

			if (sms.getMobile() != null && sms.getMobile().length > 0)
			{
				ArrayList<String> mobileContent = getContentGroup(sms.getContent());
				String[] receiver = new String[sms.getMobile().length];
				for (int i = 0; i < receiver.length; i++)
				{
					receiver[i] = "";
				}
				for (String content : mobileContent)
				{
					Object[] params = new Object[]
					{ "test", "test短信", content, "", receiver, sms.getMobile(), "test", "test" };
					Object response = call.invoke(params);
					// TODO 解析返回值，并设置返回值状态
					if ("1".equals(String.valueOf(response)))
					{
						sms.setBSuccess(true);
						for (int i = 0; i < sms.getMobile().length; i++)
						{
							myLog.info(sms.getMobile()[i] + " 发送短信成功！");
						}
					}
					else
					{
						sms.setBSuccess(false);
						for (int i = 0; i < sms.getMobile().length; i++)
						{
							myLog.info(sms.getMobile()[i] + " 发送短信失败！");
						}
						sms.setErrorInfo("发送失败！");
					}
				}
				backResult.setSendResult(sms);
			}
		}
		catch (Exception e)
		{
			sms.setBSuccess(false);
			sms.setErrorInfo(e.getMessage());
			for (int i = 0; i < sms.getMobile().length; i++)
			{
				myLog.error(sms.getMobile()[i]  + " 发送短信失败（" + e.getMessage() + "）！");
			}
			backResult.setSendResult(sms);
		}
	}
	
	private ArrayList<String> getContentGroup(String content)
	{
		ArrayList<String> contentGroup = new ArrayList<String>();
		if(content.length() > 60)
		{
			int num = content.length() / 60;
			if(content.length() % 60 == 0)
			{
				num = num - 1;
			}
			for (int i = 0; i <= num; i++)
			{
				if(i != num)
				{
					contentGroup.add(content.substring(i * 60, (i + 1) * 60) + "[" + (i + 1) + "/" + (num+1) + "]");
				}
				else
				{
					contentGroup.add(content.substring(i * 60, content.length()) + "[" + (i + 1) + "/" + (num+1) + "]");
				}
			}
		}
		else
		{
			contentGroup.add(content);
		}
		return contentGroup;
	}

	/**
	 * 接收信息
	 */
	public IMessage receive(Object msgID) throws Exception
	{
		return null;
	}

	/**
	 * 设置回调接口
	 */
	public void setCallBack(ICallBack callBack) throws Exception
	{
		backResult = callBack;
	}

}
