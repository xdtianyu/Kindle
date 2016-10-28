package org.xdty.kindle.module.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import io.requery.meta.EntityModel;
import io.requery.sql.SchemaModifier;
import io.requery.sql.TableCreationMode;

/**
 * Wrapper for requery DatabaseSource, fix the "create table" crash bug.
 */
public class DatabaseSource extends io.requery.android.sqlite.DatabaseSource {

    public DatabaseSource(Context context, EntityModel model, @Nullable String name, int version) {
        super(context, model, name, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        setTableCreationMode(TableCreationMode.CREATE_NOT_EXISTS);
        onUpgrade(db, 1, 1);
        new SchemaModifier(getConfiguration()).createTables(TableCreationMode.CREATE_NOT_EXISTS);
    }
}
