package ru.mirea.gasanyan.mireaproject;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;


public class MusicFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MusicFragment() {
        // Required empty public constructor
    }

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
    static int[] songs = {R.raw.lsp_telo,R.raw.paraoh_na_lune,R.raw.lida_pink};
    static int[] images = {R.drawable.a_photo,R.drawable.b_photo,R.drawable.c_photo};

    public static int idSong(){
        return songs[i];
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        imageView=view.findViewById(R.id.imageView);
        button =  view.findViewById(R.id.play);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getActivity().startService(new Intent(getActivity(), PlayerService.class));
            }
        });

        button =  view.findViewById(R.id.newSong);
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