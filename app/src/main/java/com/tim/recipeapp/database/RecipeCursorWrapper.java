package com.tim.recipeapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.tim.recipeapp.database.RecipeDbSchema.RecipeTable.Columns;
import com.tim.recipeapp.model.Recipe;

import java.util.UUID;

public class RecipeCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public RecipeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Recipe getRecipe(){
        String uuid = getString(getColumnIndex(Columns.UUID));
        String type = getString(getColumnIndex(Columns.TYPE));
        String title = getString(getColumnIndex(Columns.TITLE));
//        byte[] image = getBlob(getColumnIndex(Columns.IMAGE));
        String ingredient = getString(getColumnIndex(Columns.INGREDIENT));
        String steps = getString(getColumnIndex(Columns.STEPS));

        Recipe recipe = new Recipe(UUID.fromString(uuid));
        recipe.setType(type);
        recipe.setTitle(title);
//        recipe.setImage(image);
        recipe.setIngredient(ingredient);
        recipe.setStep(steps);

        return recipe;
    }
}
