package com.tim.recipeapp.activities;

import android.support.v4.app.Fragment;

import com.tim.recipeapp.fragments.RecipeListFragment;

public class FoodListActivity extends AbSingleFragmentActivity{

    @Override
    protected Fragment createFragment() {
        return new RecipeListFragment();
    }

}
