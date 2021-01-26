package ir.karcook.Tools.DownloadFileManager;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by John Cale on 2/16/2018.
 */

public class DownloadFileClass {

    private static final String TAG = "Download Task";
    private Activity context;
    private String downloadUrl = "";
    String downloadFileName = "";
    DownloadFileInterface iv;
    HttpURLConnection c;
    FileOutputStream fos;
    InputStream is;
    int current = 0;
    int len1 = 0;
    int contentLength = 0;

    public DownloadFileClass(Activity context, String downloadUrl,String fileType, DownloadFileInterface iv) {
        this.context = context;
        this.downloadUrl = downloadUrl;
        this.iv = iv;
        downloadFileName = System.currentTimeMillis()+fileType;

    }

    public void startDownload() {
        new DownloadingTask().execute();

    }

    public String getDownloadFileName() {
        return downloadFileName;
    }


    private class DownloadingTask extends AsyncTask<Void, Void, Void> {

        File outputFile = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void result) {
            try {
                if (outputFile != null) {
                    iv.downloadSuccess(downloadFileName);
                } else {
                    iv.downloadFailed("خطای نامشخص");
                    Log.e(TAG, "Download Failed");

                }
            } catch (Exception e) {
                iv.downloadFailed(e.toString());
            }


            super.onPostExecute(result);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            try {

                URL url = new URL(downloadUrl);//Create Download URl
                c = (HttpURLConnection) url.openConnection();//Open Url Connection
                c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                c.connect();//connect the URL Connection
                contentLength = c.getContentLength();
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.downloadStarted(contentLength);
                    }
                });
                Log.e(TAG, "conent : " + c.getContentLength());

                //If Connection response is not OK then show Logs
                if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    Log.e(TAG, "Server returned HTTP " + c.getResponseCode()
                            + " " + c.getResponseMessage());

                }

                File file = new File(context.getCacheDir().getAbsolutePath());


                outputFile = new File(file, downloadFileName);//Create Output file in Main File

                //Create New File if not present
                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                    Log.e(TAG, "File Created");
                } else {
                    outputFile.delete();
                    outputFile.createNewFile();
                }

                fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                is = c.getInputStream();//Get InputStream for connection

                current = 0;

                byte[] buffer = new byte[1024];//Set buffer type
                len1 = 0;//init length
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);//Write new file
                    current += len1;
                    context.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            iv.downloading(current);
                        }
                    });
                }

                //Close all connection after doing task
                fos.close();
                is.close();

            } catch (final Exception e) {

                //Read exception if something went wrong
                Log.e(TAG, e.toString());
                outputFile = null;
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iv.downloadFailed(e.toString());

                    }
                });
            }

            return null;
        }
    }

    public void cancellDownload() {
        try {
            c.disconnect();
            fos.close();
        } catch (Exception e) {
            iv.downloadFailed(e.toString());
        }
    }

    public class CheckForSDCard {
        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(

                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }
    }
}
