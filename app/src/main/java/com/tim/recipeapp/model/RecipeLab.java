package com.tim.recipeapp.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.tim.recipeapp.database.RecipeCursorWrapper;
import com.tim.recipeapp.database.RecipeDBHelper;
import com.tim.recipeapp.database.RecipeDbSchema.RecipeTable;
import com.tim.recipeapp.database.RecipeDbSchema.RecipeTable.Columns;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Processing data
 */

public class RecipeLab {
    private static RecipeLab sRecipeLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static RecipeLab get(Context context) {
        if (sRecipeLab == null) {
            sRecipeLab = new RecipeLab(context);
        }
        return sRecipeLab;
    }

    private RecipeLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new RecipeDBHelper(mContext)
                .getWritableDatabase();
    }

    public void createRecipe(Recipe c) {
        ContentValues values = getContentValues(c);
        mDatabase.insert(RecipeTable.NAME,null,values);
    }

    public void initSpicyHotpot(){
        Recipe c = new Recipe();
        String title = "Spicy Hotpot";
        String type = "Hotpot";
        String ingredient = "chili *1, water *1L, meat * 20";
        String step = "1.wash\n2.cut\n3.cook\n4.eat";
        ContentValues values = insertData(c,title,type,ingredient,step);
        mDatabase.insert(RecipeTable.NAME,null,values);
    }

    public void initMushroomSoup(){
        Recipe c = new Recipe();
        String title = "Mushroom Soup";
        String type = "Soup";
        String ingredient = "mushroom *1, vinegar *1";
        String step = "1.wash\n2.cut\n3.cook\n4.eat";
        ContentValues values = insertData(c,title,type,ingredient,step);
        mDatabase.insert(RecipeTable.NAME,null,values);
    }

    public void initFryChicken(){
        Recipe c = new Recipe();
        String title = "Fry Chicken";
        String type = "Fry";
        String ingredient = "chicken wing *6, oil *1";
        String step = "1.wash\n2.cut\n3.cook\n4.eat";
        ContentValues values = insertData(c,title,type,ingredient,step);
        mDatabase.insert(RecipeTable.NAME,null,values);
    }

    public void initMaggiNoodle(){
        Recipe c = new Recipe();
        String title = "Maggi Noodle";
        String type = "Noodle";
        String ingredient = "maggi mee *1 pack, egg *2";
        String step = "1.wash\n2.cut\n3.cook\n4.eat";
        ContentValues values = insertData(c,title,type,ingredient,step);
        mDatabase.insert(RecipeTable.NAME,null,values);
    }

    public void initChickenNugget(){
        Recipe c = new Recipe();
        String title = "Chicken Nugget";
        String type = "Snack";
        String ingredient = "chicken nugget * 20, oil * 1";
        String step = "1.wash\n2.cut\n3.cook\n4.eat";
        ContentValues values = insertData(c,title,type,ingredient,step);
        mDatabase.insert(RecipeTable.NAME,null,values);
    }

    public void initRecipe(){
        initSpicyHotpot();
        initMushroomSoup();
        initFryChicken();
        initMaggiNoodle();
        initChickenNugget();
    }

    public void updateRecipe(Recipe c){
        String uuid = c.getId().toString();
        ContentValues values = getContentValues(c);

        mDatabase.update(RecipeTable.NAME,values,
                Columns.UUID + " = ?",
                new String[]{uuid});
    }

    // 使用Cursor封装方法
    private RecipeCursorWrapper queryCrimes(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                RecipeTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new RecipeCursorWrapper(cursor);
    }

    public void deleteRecipe(Recipe c) {
        mDatabase.delete(RecipeTable.NAME, Columns.UUID + " = ?", new String[]{c.getId().toString()});
    }

    // 返回recipe列表
    public List<Recipe> getRecipeList() {
        List<Recipe> recipes = new ArrayList<>();

        try (RecipeCursorWrapper cursor = queryCrimes(null, null)) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recipes.add(cursor.getRecipe());
                cursor.moveToNext();
            }
        }

        return recipes;
    }

    public List<Recipe> getRecipeList(String type){
        List<Recipe> recipes = new ArrayList<>();

        try (RecipeCursorWrapper cursor = queryCrimes(Columns.TYPE + " = ?", new String[]{type})) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                recipes.add(cursor.getRecipe());
                cursor.moveToNext();
            }
        }

        return recipes;
    }

    // 取出已存在的首条记录
    public Recipe getRecipe(UUID id) {
        try (RecipeCursorWrapper cursor = queryCrimes(
                Columns.UUID + " = ?",
                new String[]{id.toString()}
        )) {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getRecipe();
        }
    }

    public File getPhotoFile(Recipe recipe){
        File fileDir = mContext.getFilesDir();
        return new File(fileDir,recipe.getPhotoFileName());
    }

    private static ContentValues getContentValues(Recipe recipe){
        ContentValues values = new ContentValues();
        values.put(Columns.UUID,recipe.getId().toString());
        values.put(Columns.TITLE,recipe.getTitle());
        values.put(Columns.TYPE,recipe.getType());
        values.put(Columns.INGREDIENT,recipe.getIngredient());
        values.put(Columns.STEPS,recipe.getStep());
        return values;
    }

    private static ContentValues insertData(Recipe recipe,String title,String type,String ingredient,String steps){
        ContentValues values = new ContentValues();
        values.put(Columns.UUID,recipe.getId().toString());
        values.put(Columns.TITLE,title);
        values.put(Columns.TYPE,type);
        values.put(Columns.INGREDIENT,ingredient);
        values.put(Columns.STEPS,steps);
        return values;
    }

}
