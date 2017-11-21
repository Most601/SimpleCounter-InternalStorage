package com.example.most601.simplecounterinternalstorage;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

    private Context m_context;

    public FileManager(Context context) {
        m_context = context;
    }

    //---------------------------------------------------------

    //We get a string and convert it into bytes and send them to the second function

    public void writeInternalFile(String fileName, String content, boolean append) throws IOException {
        writeInternalFile(fileName, content.getBytes(), append);
    }

    public void writeInternalFile(String fileName, byte[] content, boolean append) throws IOException {
        // Context.MODE_PRIVATE = 0, therefore, we don't need to explicitly specify it.
        //Object writing to file
        //Using m_context so that android will knows which application we're talking about,
        FileOutputStream outputStream = m_context.openFileOutput(fileName, append ? Context.MODE_APPEND : Context.MODE_PRIVATE);
        //write down the data
        outputStream.write(content);
        outputStream.close();
    }

    //----------------------------------------------------------

    public String readInternalFile(String fileName) throws IOException {
        String content = "";
        //same as FileOutputStream gast now is FileInputStream
        FileInputStream inputStream = m_context.openFileInput(fileName);
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder stringBuilder = new StringBuilder();
            while ((content = bufferedReader.readLine()) != null) {
                stringBuilder.append(content);
            }

            // Releasing resources.
            bufferedReader.close();
            streamReader.close();
            inputStream.close();

            content = stringBuilder.toString();
        }

        return content;
    }

    public boolean deleteInternalFile(String fileName) {
        return m_context.deleteFile(fileName);
    }

    String[] getInternalFileList() {
        return m_context.fileList();
    }

}