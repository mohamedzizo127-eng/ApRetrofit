package com.example.apretrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
 import android.os.Bundle;
 import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.apretrofit.helpers.SampleContent;
import com.example.apretrofit.models.Idea;
import com.example.apretrofit.retrofit.IdeaService;
import com.example.apretrofit.retrofit.ServiceBuilder;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IdeaDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";

    private Idea mItem;
    private int mMId;

    public IdeaDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.idea_detail, container, false);

        final Context context = getContext();

        final Button updateIdea = (Button) rootView.findViewById(R.id.idea_update);
        Button deleteIdea = (Button) rootView.findViewById(R.id.idea_delete);

        final EditText ideaName = (EditText) rootView.findViewById(R.id.idea_name);
        final EditText ideaDescription = (EditText) rootView.findViewById(R.id.idea_description);
        final EditText ideaStatus = (EditText) rootView.findViewById(R.id.idea_status);
        final EditText ideaOwner = (EditText) rootView.findViewById(R.id.idea_owner);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            Activity activity = this.getActivity();
            final CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
            mMId = getArguments().getInt(ARG_ITEM_ID);
            Call<Idea> request = ideaService.getIdea(mMId);
            request.enqueue(new Callback<Idea>() {
                @Override
                public void onResponse(Call<Idea> call, Response<Idea> response) {
                    mItem = response.body();
                    ideaName.setText(mItem.getName());
                    ideaDescription.setText(mItem.getDescription());
                    ideaOwner.setText(mItem.getOwner());
                    ideaStatus.setText(mItem.getStatus());
                    if (appBarLayout != null) {
                        appBarLayout.setTitle(mItem.getName());
                    }

                }

                @Override
                public void onFailure(Call<Idea> call, Throwable t) {
                    Toast.makeText(context, "Failed to retrieve item.", Toast.LENGTH_SHORT).show();


                }
            });
//            mItem = SampleContent.getIdeaById(getArguments().getInt(ARG_ITEM_ID));




        }

        updateIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Idea newIdea = new Idea();
                newIdea.setId(mMId);
                newIdea.setName(ideaName.getText().toString());
                newIdea.setDescription(ideaDescription.getText().toString());
                newIdea.setStatus(ideaStatus.getText().toString());
                newIdea.setOwner(ideaOwner.getText().toString());

               // SampleContent.updateIdea(newIdea);
                IdeaService updateServise = ServiceBuilder.buildService(IdeaService.class);
                Call<Idea> updateRequest = updateServise.updateIdea(mMId,newIdea.getDescription(),
                        newIdea.getStatus(), newIdea.getOwner(), newIdea.getName());
                updateRequest.enqueue(new Callback<Idea>() {
                    @Override
                    public void onResponse(Call<Idea> call, Response<Idea> response) {
                        Idea idea = response.body();

                        Intent intent = new Intent(getContext(), IdeaListActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Idea> call, Throwable t) {

                    }
                });



            }
        });

        deleteIdea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // SampleContent.deleteIdea(getArguments().getInt(ARG_ITEM_ID));
                IdeaService ideaService = ServiceBuilder.buildService(IdeaService.class);
                Call<Void> deleteRequest = ideaService.deleteIdea(mMId);
                deleteRequest.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
                Intent intent = new Intent(getContext(), IdeaListActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
