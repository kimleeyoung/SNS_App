package com.kly.mjstargram;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.ACTION_PICK;

public class AddFragment extends Fragment {

    ImageView imageIv;
    EditText messageEt;
    Button submitBtn;
    final int REQ_IMAGE_PICK=1000;

    Uri selectedImage;

    FirebaseStorage storage=FirebaseStorage.getInstance();
    FirebaseAuth auth= FirebaseAuth.getInstance();
    FirebaseFirestore firestore= FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_add,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageIv=view.findViewById(R.id.image_iv);
        messageEt=view.findViewById(R.id.message_et);
        submitBtn=view.findViewById(R.id.submit_btn);
        imageIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,REQ_IMAGE_PICK);
            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedImage==null){
                    Toast.makeText(getActivity(),"이미지를 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                String fileName=UUID.randomUUID().toString();
                storage.getReference().child("post").child(fileName).putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                taskSnapshot.getMetadata().getReference().getDownloadUrl()
                                        .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String imageUrl=uri.toString();
                                                Log.d("AddFragment",imageUrl);
                                                uploadPost(imageUrl);
                                            }
                                        });
                            }
                        });
            }
        });
    }

    public void uploadPost(String imageUrl){
        Post post=new Post();
        String message=messageEt.getText().toString();
        post.setMessage(message);
        post.setImageUrl(imageUrl);

        String writerId=auth.getCurrentUser().getEmail();

        post.setWriterId(writerId);
        firestore.collection("post").document().set(post)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        imageIv.setImageDrawable(getActivity().getDrawable(R.drawable.baseline_add_circle_outline_black_48));
                        messageEt.setText("");
                        MainActivity mainActivity=(MainActivity) getActivity();
                        mainActivity.moveTab(0);
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ_IMAGE_PICK && resultCode == RESULT_OK){
            imageIv.setImageURI(data.getData());
            selectedImage=data.getData();

        }
    }
}