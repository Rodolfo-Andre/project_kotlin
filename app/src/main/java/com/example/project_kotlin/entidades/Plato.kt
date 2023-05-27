package com.example.project_kotlin.entidades

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "Plato",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaPlato::class,
            parentColumns = ["id"],
            childColumns = ["id_cat_plato"]
        )
    ])
class Plato (
    @PrimaryKey var id: String,
    @NonNull @ColumnInfo(name = "nom_plato") var nombrePlato : String,
    @NonNull @ColumnInfo(name="precio_plato") var precioPlato : Double,
    @NonNull @ColumnInfo(name="nom_imagen") var nombreImagen : String,
    @NonNull @ColumnInfo(name="id_cat_plato") var idCatPlato : String){

}