package com.tim.recipeapp.activities;


import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.tim.recipeapp.R;
import com.tim.recipeapp.fragments.RecipeListFragment;
import com.tim.recipeapp.model.RecipeLab;

public class MainActivity extends AppCompatActivity {
    private ListView mCategory;
    public static final String EXTRA_CATEGORY = "extra_category";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final String[] mItems = getResources().getStringArray(R.array.category);
        mCategory = findViewById(R.id.list_category);
        ListAdapter adapter = new ArrayAdapter<>(getApplication(),android.R.layout.simple_list_item_1,mItems);
        mCategory.setAdapter(adapter);

        RecipeLab lab = RecipeLab.get(getApplicationContext());
        lab.initRecipe();

        mCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String msg = mCategory.getItemAtPosition(position).toString();
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                RecipeListFragment fragment = RecipeListFragment.newInstance(msg);//如果不想传值，给过去一个null就行
                transaction.add(fragment,msg);
                transaction.commit();
                startActivity(new Intent(getApplicationContext(),FoodListActivity.class));
            }
        });
    }
}
