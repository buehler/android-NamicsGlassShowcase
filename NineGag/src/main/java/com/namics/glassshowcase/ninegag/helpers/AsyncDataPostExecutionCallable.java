package com.namics.glassshowcase.ninegag.helpers;

public interface AsyncDataPostExecutionCallable<T>{

	public void call(T asyncResult) throws Exception;

}
