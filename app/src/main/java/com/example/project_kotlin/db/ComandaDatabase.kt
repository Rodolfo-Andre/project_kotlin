package com.example.project_kotlin.db

import android.content.Context
import androidx.room.*
import com.example.project_kotlin.dao.*
import com.example.project_kotlin.entidades.*
import java.util.*

@Database(
    entities = [Apertura::class, Caja::class,
        Cargo::class, Comanda::class,
        Comprobante::class, Usuario::class,
        CategoriaPlato::class, Mesa::class,
        DetalleComprobante::class, DetalleComanda::class,
        Empleado::class, Plato::class, Establecimiento::class,
        EstadoComanda::class, MetodoPago::class, TipoComprobante::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class ComandaDatabase : RoomDatabase() {
    abstract fun aperturaDao() : AperturaDao
    abstract fun cajaDao() : CajaDao
    abstract fun cargoDao() : CargoDao
    abstract fun comandaDao() : ComandaDao
    abstract fun comprobanteDao() : ComprobanteDao
    abstract fun usuarioDao() : UsuarioDao
    abstract fun categoriaPlatoDao() : CategoriaPlatoDao
    abstract fun mesaDao() : MesaDao

    //Entidades de Jorge Chavez
    abstract  fun platoDao() : PlatoDao


    //Entidades de Gary Morales
    abstract fun metodoPagoDao() : MetodoPagoDao
    abstract fun tipoComprobanteDao() : TipoComprobanteDao
    abstract fun estadoComandaDao(): EstadoComandaDao
    abstract fun establecimientoDao() : EstablecimientoDao
    abstract fun platoDao() : PlatoDao
    abstract fun empleadoDao() : EmpleadoDao
    abstract fun detalleComandaDao() : DetalleComandaDao
    abstract fun detalleComprobanteDao() : DetalleComprobanteDao

    companion object {
        @Volatile
        private var INSTANCIA: ComandaDatabase? = null

        fun obtenerBaseDatos(context: Context): ComandaDatabase {
            return INSTANCIA ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    ComandaDatabase::class.java,
                    "comanda_database"
                ) .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCIA = it }
            }
        }
    }
}

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.time
    }
}