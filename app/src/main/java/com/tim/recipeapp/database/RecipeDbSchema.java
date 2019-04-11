package com.tim.recipeapp.database;

public class RecipeDbSchema {
    public static final class RecipeTable {
        public static final String NAME = "recipes";

        public static final class Columns {
            public static final String UUID = "uuid";
            public static final String TYPE = "type";
            public static final String TITLE = "title";
//            public static final String IMAGE = "image";
            public static final String INGREDIENT = "ingredient";
            public static final String STEPS = "steps";
        }
    }
}
