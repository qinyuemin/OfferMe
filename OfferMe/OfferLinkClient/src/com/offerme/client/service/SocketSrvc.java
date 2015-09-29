package com.offerme.client.service;

public interface SocketSrvc {
	public void onPreExecute();

	public Boolean doInBackground(String... params);

	public void onProgressUpdate(Integer... values);

	public void onPostExecute(final Boolean success);

	public void onCancelled();
}
