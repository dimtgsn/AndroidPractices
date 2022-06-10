package ru.mirea.gasanyan.mireaproject;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RecordAudioFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PERMISSION = 100;
    private Button startRecord, stopRecord, startListening, stopListening;
    private MediaRecorder mediaRecorder;
    private File audioFile;
    private String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };
    private boolean isWork;
    MediaPlayer mediaPlayer;

    public RecordAudioFragment() { }

    public static RecordAudioFragment newInstance(String param1, String param2) {
        RecordAudioFragment fragment = new RecordAudioFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    public static boolean hasPermissions(Context context, String[] permissions) {
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_audio, container, false);

        startListening = view.findViewById(R.id.startListening);
        stopListening = view.findViewById(R.id.stopListening);

        startRecord = view.findViewById(R.id.startRecord);
        stopRecord = view.findViewById(R.id.stopRecord);

        mediaRecorder = new MediaRecorder();

        isWork = hasPermissions(getActivity().getApplicationContext(), PERMISSIONS);
        if (!isWork) {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, REQUEST_CODE_PERMISSION);
        }

        startRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startRecord.setEnabled(false);
                    stopRecord.setEnabled(true);
                    stopRecord.requestFocus();
                    startRecording();
                } catch (Exception e) {
                    Log.e(TAG, "I/O exception " + e.getMessage());
                }
            }
        });

        stopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRecord.setEnabled(true);
                stopRecord.setEnabled(false);
                startRecord.requestFocus();
                stopRecording();
                processAudioFile();

            }
        });

        startListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mediaPlayer = new MediaPlayer();
                    mediaPlayer.setDataSource(String.valueOf(audioFile));
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        stopListening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
            }
        });
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            isWork = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void startRecording() throws IOException {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            Log.d(TAG, "sd-card success");
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            if (audioFile == null) {
                audioFile = new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_MUSIC), "mirea.5gp");
            }
            mediaRecorder.setOutputFile(audioFile.getAbsolutePath());
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(getActivity(), "Recording started!", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            Log.d(TAG, "stopRecording");
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            Toast.makeText(getActivity(), "Currently not recording!", Toast.LENGTH_SHORT).show();
        }
    }

    private void processAudioFile() {
        Log.d(TAG, "processAudioFile");

        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audioFile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));

        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/5gp");
        values.put(MediaStore.Audio.Media.DATA, audioFile.getAbsolutePath());

        ContentResolver contentResolver = getActivity().getContentResolver();
        Log.d(TAG, "audioFile: " + audioFile.canRead()+audioFile.getName());
        Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(baseUri, values);
        getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
    }
}