package ir.karcook.Tools.DownloadFileManager;

public interface DownloadFileInterface {

    void downloadStarted(int contentLength);
    void downloading(int current);
    void downloadFailed(String s);
    void downloadSuccess(String downloadFileName);


}
