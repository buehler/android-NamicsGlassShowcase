package com.namics.glassshowcase.namicslivewordmark;

public interface AsyncDataPostExecutionCallable<T>{

	public void call(T asyncResult) throws Exception;

}
