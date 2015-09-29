package com.offerme.client.service.socket;

import com.offerme.client.service.SocketSrvc;

import android.os.AsyncTask;

public class SocketCommunication extends AsyncTask<String, Integer, Boolean> {

	private SocketSrvc socketSrvc;

	public SocketCommunication(SocketSrvc service) {
		socketSrvc = service;
	}

	@Override
	protected void onPreExecute() {
		socketSrvc.onPreExecute();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		return socketSrvc.doInBackground(params);
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		//super.onProgressUpdate(values);
		socketSrvc.onProgressUpdate(values);
	}

	@Override
	protected void onPostExecute(final Boolean success) {
		socketSrvc.onPostExecute(success);
		this.cancel(true);
	}

	@Override
	protected void onCancelled() {
		socketSrvc.onCancelled();
		this.cancel(true);
	}

	public void notifyProgress(Integer values) {
		this.publishProgress(values);
	}
}
