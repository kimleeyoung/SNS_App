package com.kly.mjstargram;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    RecyclerView timelineRv;
    List<Post> postList=new ArrayList<>();
    timelineAdapter timelineAdapter;

    FirebaseFirestore firestore=FirebaseFirestore.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timelineRv=view.findViewById(R.id.timeline_rv);
        timelineAdapter=new timelineAdapter(getActivity(),postList);

        timelineRv.setAdapter(timelineAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        timelineRv.setLayoutManager(layoutManager);

        getPostList();
    }

    public void getPostList(){
        firestore.collection("post")
                .orderBy("uploadDate", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentList=queryDocumentSnapshots.getDocuments();
                        postList.clear();
                        for(DocumentSnapshot ds:documentList){
                            Post post=ds.toObject(Post.class);
                            postList.add(post);
                        }
                        timelineAdapter.notifyDataSetChanged();
                    }
                });
    }

}
