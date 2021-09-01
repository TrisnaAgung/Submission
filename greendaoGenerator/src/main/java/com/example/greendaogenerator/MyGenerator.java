package com.example.greendaogenerator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyGenerator {
    public static void main(String[] args) {
        Schema schema = new Schema(2, "com.example.submission.database"); // Your app package name and the (.db) is the folder where the DAO files will be generated into.
        schema.enableKeepSectionsByDefault();

        addTables(schema);

        try {
            new DaoGenerator().generateAll(schema,"../app/src/main/java");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void addTables(final Schema schema) {
        addFavoriteEntities(schema);
    }

    private static Entity addFavoriteEntities(final Schema schema) {
        Entity favorite = schema.addEntity("Favorite");
        favorite.addImport("com.google.gson.annotations.SerializedName");
        favorite.addIdProperty().primaryKey().autoincrement();
        favorite.addStringProperty("login").codeBeforeField("@SerializedName( \"login\" )");
        favorite.addStringProperty("node_id").codeBeforeField("@SerializedName( \"node_id\" )");
        favorite.addStringProperty("avatar_url").codeBeforeField("@SerializedName( \"avatar_url\" )");
        favorite.addStringProperty("gravatar_id").codeBeforeField("@SerializedName( \"gravatar_id\" )");
        favorite.addStringProperty("url").codeBeforeField("@SerializedName( \"url\" )");
        favorite.addStringProperty("html_url").codeBeforeField("@SerializedName( \"html_url\" )");
        favorite.addStringProperty("followers_url").codeBeforeField("@SerializedName( \"followers_url\" )");
        favorite.addStringProperty("following_url").codeBeforeField("@SerializedName( \"following_url\" )");
        favorite.addStringProperty("gists_url").codeBeforeField("@SerializedName( \"gists_url\" )");
        favorite.addStringProperty("starred_url").codeBeforeField("@SerializedName( \"starred_url\" )");
        favorite.addStringProperty("subscriptions_url").codeBeforeField("@SerializedName( \"subscriptions_url\" )");
        favorite.addStringProperty("organizations_url").codeBeforeField("@SerializedName( \"organizations_url\" )");
        favorite.addStringProperty("repos_url").codeBeforeField("@SerializedName( \"repos_url\" )");
        favorite.addStringProperty("events_url").codeBeforeField("@SerializedName( \"events_url\" )");
        favorite.addStringProperty("received_events_url").codeBeforeField("@SerializedName( \"received_events_url\" )");
        favorite.addStringProperty("type").codeBeforeField("@SerializedName( \"type\" )");
        favorite.addBooleanProperty("site_admin").codeBeforeField("@SerializedName( \"site_admin\" )");
        favorite.addStringProperty("name").codeBeforeField("@SerializedName( \"name\" )");
        favorite.addStringProperty("company").codeBeforeField("@SerializedName( \"company\" )");
        favorite.addStringProperty("blog").codeBeforeField("@SerializedName( \"blog\" )");
        favorite.addStringProperty("location").codeBeforeField("@SerializedName( \"location\" )");
        favorite.addStringProperty("email").codeBeforeField("@SerializedName( \"email\" )");
        favorite.addStringProperty("hireable").codeBeforeField("@SerializedName( \"hireable\" )");
        favorite.addStringProperty("bio").codeBeforeField("@SerializedName( \"bio\" )");
        favorite.addStringProperty("twitter_username").codeBeforeField("@SerializedName( \"twitter_username\" )");
        favorite.addIntProperty("public_repos").codeBeforeField("@SerializedName( \"public_repos\" )");
        favorite.addIntProperty("public_gists").codeBeforeField("@SerializedName( \"public_gists\" )");
        favorite.addIntProperty("followers").codeBeforeField("@SerializedName( \"followers\" )");
        favorite.addIntProperty("following").codeBeforeField("@SerializedName( \"following\" )");
        favorite.addStringProperty("created_at").codeBeforeField("@SerializedName( \"created_at\" )");
        favorite.addStringProperty("updated_at").codeBeforeField("@SerializedName( \"updated_at\" )");
        favorite.addContentProvider();

        return favorite;
    }
}