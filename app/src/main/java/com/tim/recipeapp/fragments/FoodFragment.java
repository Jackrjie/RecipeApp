package com.tim.recipeapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tim.recipeapp.R;
import com.tim.recipeapp.activities.MainActivity;
import com.tim.recipeapp.model.Recipe;
import com.tim.recipeapp.model.RecipeLab;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

import static android.text.TextUtils.isEmpty;

public class FoodFragment extends Fragment {

    private static final String TAG = "FoodFragment";
    private Recipe mRecipe;
    private File mPhotoFile;
    private EditText mTitleEditText,mIngredientEditText,mStepsEditText;
    private TextView mTypeTextView;
    private ImageView mFoodImageView;
    private Spinner mTypeSpinner;
    private Button mCameraButton,mGalleryButton,mSaveButton;
    private FloatingActionButton mEditActionButton;
    private String mTypeValue;


    private final static String ARG_FOOD_ID = "arg_food_id";
    private static final String DIALOG_IMAGE = "DialogImage";
    private static final int REQUEST_CAMERA = 4;

    public static FoodFragment newInstance(UUID foodId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_FOOD_ID,foodId);

        FoodFragment foodFragment = new FoodFragment();
        foodFragment.setArguments(args);
        return foodFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        UUID recipeID = (UUID) (getArguments() != null ? getArguments().getSerializable(ARG_FOOD_ID) : null);
        mRecipe = RecipeLab.get(getActivity()).getRecipe(recipeID);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_food,container,false);

        mTitleEditText = v.findViewById(R.id.et_food_name);
        mTitleEditText.setText(mRecipe.getTitle());

        mTypeSpinner = v.findViewById(R.id.spn_type);
        mTypeValue = null;
        String[] mItems = getResources().getStringArray(R.array.category);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTypeSpinner.setAdapter(adapter);
        mTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mTypeValue = mTypeSpinner.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mIngredientEditText = v.findViewById(R.id.et_food_ingredient);
        mIngredientEditText.setText(mRecipe.getIngredient());

        mTypeTextView = v.findViewById(R.id.tv_type);
        mTypeTextView.setText(mRecipe.getType());

        mStepsEditText = v.findViewById(R.id.et_food_step);
        mStepsEditText.setText(mRecipe.getStep());

        mEditActionButton = v.findViewById(R.id.fab_edit_recipe);
        mCameraButton = v.findViewById(R.id.btn_camera);
        mGalleryButton = v.findViewById(R.id.btn_gallery);
        mSaveButton = v.findViewById(R.id.btn_save);

        mTitleEditText.setEnabled(false);
        mIngredientEditText.setEnabled(false);
        mStepsEditText.setEnabled(false);

        mEditActionButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                mTitleEditText.setEnabled(true);
                mTypeSpinner.setEnabled(true);
                mIngredientEditText.setEnabled(true);
                mStepsEditText.setEnabled(true);
                mTypeTextView.setVisibility(View.GONE);
                mCameraButton.setVisibility(View.VISIBLE);
                mGalleryButton.setVisibility(View.VISIBLE);
                mTypeSpinner.setVisibility(View.VISIBLE);
                mSaveButton.setVisibility(View.VISIBLE);
                mEditActionButton.setVisibility(View.INVISIBLE);

                mSaveButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = mTitleEditText.getText().toString();
                        String ingre = mIngredientEditText.getText().toString();
                        String steps = mStepsEditText.getText().toString();

                        if (!isEmpty(title) && !isEmpty(mTypeValue) && !isEmpty(ingre) && !isEmpty(steps)){
                            mRecipe.setTitle(title);
                            mRecipe.setType(mTypeValue);
                            mRecipe.setIngredient(ingre);
                            mRecipe.setStep(steps);
                        } else {
                            Toast.makeText(getActivity(),"All field cannot be empty",Toast.LENGTH_SHORT).show();
                            RecipeLab.get(getActivity()).deleteRecipe(mRecipe);
                            getActivity().finish();
                        }
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();

        RecipeLab.get(getActivity())
                .updateRecipe(mRecipe);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_item,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_delete:
                RecipeLab.get(getActivity()).deleteRecipe(mRecipe);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
