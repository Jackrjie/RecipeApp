package com.tim.recipeapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tim.recipeapp.R;
import com.tim.recipeapp.activities.FoodPagerActivity;
import com.tim.recipeapp.model.Recipe;
import com.tim.recipeapp.model.RecipeLab;

import java.util.List;

public class RecipeListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecipeAdapter mAdapter;
    private FloatingActionButton mActionButton;
    private int mLastClickPosition = -1;
    private String[] mCategory;
    private static final String TAG = "RecipeListFragmentX";
    private String strType;
    public static final String MARK = "mark";


    public static RecipeListFragment newInstance(String value)
    {
        //将fragment绑定参数
        Bundle bundle = new Bundle();
        bundle.putString(MARK, value);
        RecipeListFragment fragment = new RecipeListFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_list,container,false);

        Bundle bundle = this.getArguments();
        if (bundle != null)
        {
            strType = bundle.getString(MARK);
            Log.d(TAG, "onCreateView: " + strType);
        }

        // init recyclerView
        mRecyclerView = v.findViewById(R.id.rv_food_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mActionButton = v.findViewById(R.id.fab_create_recipe);
        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewRecipe();
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private Recipe mRecipe;

        public RecipeHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_food,parent,false));
            itemView.setOnClickListener(this);
            mTitleTextView = itemView.findViewById(R.id.tv_list_food_name);
        }

        public void bind(Recipe recipe){
            mRecipe = recipe;
            mTitleTextView.setText(mRecipe.getTitle());
        }

        @Override
        public void onClick(View v) {
            mLastClickPosition = this.getAdapterPosition();
            startActivity(FoodPagerActivity.newIntent(getActivity(),mRecipe.getId()));
        }
    }

    private class RecipeAdapter extends RecyclerView.Adapter<RecipeHolder>{

        private List<Recipe> mRecipes;

        RecipeAdapter(List<Recipe> recipes){
            mRecipes = recipes;
        }

        @NonNull
        @Override
        public RecipeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new RecipeHolder(inflater,viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull RecipeHolder recipeHolder, int i) {
            Recipe recipe = mRecipes.get(i);
            recipeHolder.bind(recipe);
        }

        @Override
        public int getItemCount() {
            return mRecipes.size();
        }

        public void setRecipes(List<Recipe> recipes) {
            mRecipes = recipes;
        }
    }

    private void createNewRecipe(){
        Recipe recipe = new Recipe();
        RecipeLab.get(getActivity()).createRecipe(recipe);
        Intent intent = FoodPagerActivity.newIntent(getActivity(),recipe.getId());
        startActivity(intent);
    }

    private void updateUI(){
        RecipeLab lab = RecipeLab.get(getActivity());
        List<Recipe> recipes;
        Log.d(TAG, "updateUI: " + strType);

        mCategory = getResources().getStringArray(R.array.category);

        recipes = lab.getRecipeList(); // **************

        if (mAdapter == null) {
            mAdapter = new RecipeAdapter(recipes);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setRecipes(recipes);
            if (mLastClickPosition > -1){
                mAdapter.notifyItemChanged(mLastClickPosition);
                mLastClickPosition = -1;
            } else {
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
