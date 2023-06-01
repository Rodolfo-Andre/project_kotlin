package com.example.project_kotlin.entidades

import androidx.annotation.NonNull
import androidx.room.*

@Entity(tableName = "Plato",
    foreignKeys = [
        ForeignKey(
            entity = CategoriaPlato::class,
            parentColumns = ["id"],
            childColumns = ["catplato_id"]
        )
    ])
class Plato (
    @PrimaryKey var id: String,
    @NonNull @ColumnInfo(name = "nom_plato") var nombrePlato : String,
    @NonNull @ColumnInfo(name="precio_plato") var precioPlato : Double,
    @NonNull @ColumnInfo(name="nom_imagen") var nombreImagen : ByteArray):java.io.Serializable{
    @Embedded(prefix = "catplato_")
    lateinit var categoriaPlato: CategoriaPlato

    companion object {
        fun generarCodigo(listaPlatos: List<Plato>): String {
            if (listaPlatos.isEmpty()) return "P-001"

            val ultimoCodigo = listaPlatos.last().id
            val numero = ultimoCodigo.split('-')[1].toInt() + 1

            return "P-${String.format("%03d", numero)}"
        }
    }

}