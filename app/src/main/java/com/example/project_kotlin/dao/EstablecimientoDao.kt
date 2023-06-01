package com.example.project_kotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project_kotlin.entidades.*

@Dao
interface EstablecimientoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun guardar(establecimiento: Establecimiento) : Long
    @Query("select * from Establecimiento")
    fun obtenerTodoLiveData(): LiveData<List<Establecimiento>>

    @Query("select *from Establecimiento")
    fun listar():Establecimiento


    @Query("SELECT * FROM Establecimiento WHERE id = :id")
    fun obtenerPorId(id: Long) : Establecimiento

    @Update
    fun actualizar(establecimiento: Establecimiento)

    @Delete
    fun eliminar(establecimiento: Establecimiento)
}