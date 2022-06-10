package ru.mirea.gasanyan.mireaproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


public class MusicFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MusicFragment() { }

    public static MusicFragment newInstance(String param1, String param2) {
        MusicFragment fragment = new MusicFragment();
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

    private Button button;
    private ImageView imageView;
    static int i=0;
    static int[] songs = {R.raw.dance_monkey,R.raw.industry_baby,R.raw.bones};
    static int[] images = {R.drawable.lil_nas_x_industry_baby,R.drawable.imagine_dragons_bones,R.drawable.tones_and_i_dance_monkey};

    public static int idSong(){
        return songs[i];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        imageView = view.findViewById(R.id.imageView);
        button =  view.findViewById(R.id.play);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), PlayerService.class));
            }
        });

        button =  view.findViewById(R.id.nextSong);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), PlayerService.class));
                i++;
                if (i>2)
                    i=0;
                imageView.setImageResource(images[i]);
            }
        });

        button =  view.findViewById(R.id.stop);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getActivity().stopService(new Intent(getActivity(), PlayerService.class));
            }
        });
        return view;
    }
}