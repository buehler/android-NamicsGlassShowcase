package com.namics.glassshowcase.namicslivewordmark;

import java.util.concurrent.Callable;

import android.os.AsyncTask;

public class AsyncDataTask<T> extends AsyncTask<Void, Void, T> {

	private Callable<Void> preExecuteTask;
	private Callable<T> backgroundTask;
	private AsyncDataPostExecutionCallable<T> postExecuteTask;
	
	public AsyncDataTask(Callable<T> backgroundTask){
		this.backgroundTask = backgroundTask;
	}
	
	public AsyncDataTask(Callable<T> backgroundTask, AsyncDataPostExecutionCallable<T> postExecuteTask){
		this(backgroundTask);
		this.postExecuteTask = postExecuteTask;
	}
	
	public AsyncDataTask(Callable<T> backgroundTask, AsyncDataPostExecutionCallable<T> postExecuteTask, Callable<Void> preExecuteTask){
		this(backgroundTask, postExecuteTask);
		this.preExecuteTask = preExecuteTask;
	}
	
	@Override
	protected void onPreExecute() {
		if(preExecuteTask != null){
			try {
				preExecuteTask.call();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onPreExecute();
	}

	@Override
	protected T doInBackground(Void... params) {
		try {
			return backgroundTask.call();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPostExecute(T result) {
		if(postExecuteTask != null){
			try {
				postExecuteTask.call(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		super.onPostExecute(result);
	}

}
