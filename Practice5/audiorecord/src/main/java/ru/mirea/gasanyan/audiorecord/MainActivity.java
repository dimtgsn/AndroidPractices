package ru.mirea.gasanyan.audiorecord;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION = 100;
    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private boolean isWork;
    private Button startRecordButton;
    private Button stopRecordButton;
    private MediaRecorder mediaRecorder;
    private File audioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isWork = hasPermissions(this, PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(this, PERMISSIONS,
                    REQUEST_CODE_PERMISSION);
        }
        startRecordButton = findViewById(R.id.btnStart);
        stopRecordButton = findViewById(R.id.btnStop);
        mediaRecorder = new MediaRecorder();
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            // permission granted
            isWork = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    public void onRecordStart(View view) {
        try {
            startRecordButton.setEnabled(false);
            stopRecordButton.setEnabled(true);
            stopRecordButton.requestFocus();
            startRecording();
        } catch (Exception e) {
            Log.e(TAG, "Caught io exception " + e.getMessage());
        }
    }
    public void onStopRecord(View view) {
        startRecordButton.setEnabled(true);
        stopRecordButton.setEnabled(false);
        startRecordButton.requestFocus();
        stopRecording();
        processAudioFile();
    }
    private void startRecording() throws IOException {
        // проверка доступности sd - карты
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            // выбор источника звука
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            // выбор формата данных
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // выбор кодека
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                // создание файла
                audioFile = new File(this.getExternalFilesDir(
                        Environment.DIRECTORY_MUSIC), "mirea.5gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(this, "You are not recording right now!",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        // установка meta данных созданному файлу
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/5gpp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());
        ContentResolver contentResolver = getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        // оповещение системы о новом файле
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }
}

