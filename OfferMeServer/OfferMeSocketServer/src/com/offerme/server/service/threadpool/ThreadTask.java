package com.offerme.server.service.threadpool;

import java.net.Socket;

import com.offerme.server.MainServer;
import com.offerme.server.socket.SocketReadWrite;
import com.offerme.server.util.Log;

public class ThreadTask implements Runnable  {

	static Log myLog = new Log("com.offerme.database.threadpool");
	private MainServer server =null;
	Socket clientSocket ;
	private String clientIp = null;
	
	public ThreadTask(MainServer server, Socket clientSocket) {
		this.server = server;
		this.clientSocket = clientSocket;
		this.clientIp = null;
	}
	
	@Override
	public void run() {
		
		if(clientSocket == null)
		{
			return;
		}
		try {
			
			if(clientSocket != null)
			{
				clientIp = clientSocket.getInetAddress().getHostAddress();
				String strRequest = SocketReadWrite.SocketRead(clientSocket);
				//System.out.println("Request recevie: "+strRequest);//Added by Yuemin QIN to find the request read from socket
				Request request = Request.getRequest(strRequest);
				Reply reply = new Reply();
				try {
					DistributeManage.DistributeRequest(request, reply, clientIp);
					//System.out.println("Request reply is: "+reply.getJsonReply());
					myLog.info(reply.getJsonReply());// log json of reply
				} catch (Exception e) {
					
				}
				finally
				{
					Request.clearRequest();
				}
				SocketReadWrite.SocketWrite(clientSocket, reply.getJsonReply());
			}
			
		} catch (Exception e) {
			myLog.error(Log.getStackInfo(e));
			myLog.error("Logis Server failed in Listener:" + e.getLocalizedMessage());
		}
		
	
	}
}
