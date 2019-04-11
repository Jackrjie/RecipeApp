package com.tim.recipeapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.tim.recipeapp.R;
import com.tim.recipeapp.fragments.FoodFragment;
import com.tim.recipeapp.model.Recipe;
import com.tim.recipeapp.model.RecipeLab;

import java.util.List;
import java.util.UUID;

public class FoodPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<Recipe> mRecipes;
    public static final String EXTRA_RECIPE_ID = "com.tim.recipeapp.recipe_id";
    private static final String TAG = "FoodPagerActivity";

    public static Intent newIntent(Context context, UUID recipeID){
        Intent intent = new Intent(context,FoodPagerActivity.class);
        intent.putExtra(EXTRA_RECIPE_ID,recipeID);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_pager);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        UUID recipeID = (UUID)getIntent().getSerializableExtra(EXTRA_RECIPE_ID);

        mRecipes = RecipeLab.get(this).getRecipeList();

        mViewPager = findViewById(R.id.vp_food_activity);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int i) {
                Recipe crime = mRecipes.get(i);
                return FoodFragment.newInstance(crime.getId());
            }

            @Override
            public int getCount() {
                return mRecipes.size();
            }
        });

        // 点哪个开哪个
        for (int i = 0; i < mRecipes.size(); i++) {
            if (mRecipes.get(i).getId().equals(recipeID)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

    }


}
