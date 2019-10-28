package com.example.activitytest.downloadtest;

/**
 * 用于控制下载的进度,属于回调接口
 */
public interface DownloadListener {
    void onProgress(int progress); //下载进度
    void  onSuccess(); //成功
    void onFailed(); //失败
    void onPaused(); //暂停
    void onCancle();//取消


}
