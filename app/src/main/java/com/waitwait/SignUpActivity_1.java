package com.waitwait;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.navigation.Navigation;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpActivity_1 extends Fragment {


    public SignUpActivity_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up_activity_1, container, false);


        Button button = (Button) view.findViewById(R.id.WaitWaitStartButton);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Toast.makeText(getActivity(), "응 버튼 눌렸어", Toast.LENGTH_LONG).show();
                Navigation.findNavController(v).navigate(R.id.action_signUpActivity_1_to_signUpActivity_2);
            }
        });
        return view;


    }




}
