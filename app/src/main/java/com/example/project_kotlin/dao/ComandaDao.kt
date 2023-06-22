package com.example.project_kotlin.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.project_kotlin.entidades.Comanda
import com.example.project_kotlin.entidades.ComandaMesaYEmpleadoYEstadoComanda
import com.example.project_kotlin.entidades.relaciones.ComandaConMesa

@Dao
interface ComandaDao {
    @Transaction
    @Query("SELECT * FROM comanda")
     fun obtenerTodo() : LiveData<List<ComandaConMesa>>

     /*
     * @Query("SELECT * FROM comanda")
     fun obtenerTodoLiveData() : LiveData<List<ComandaMesaYEmpleadoYEstadoComanda>>
     *  * @Query("SELECT * FROM comanda")
     fun obtenerTodo() :List<ComandaMesaYEmpleadoYEstadoComanda>
     *
     * */
    @Query("SELECT * FROM comanda WHERE id = :id")
     fun obtenerPorId(id: Long) : ComandaConMesa

     @Query("SELECT * FROM comanda WHERE mesa_id = :mesa_id")
     fun obtenerComandasPorMesa(mesa_id: Int) : List<ComandaConMesa>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
     fun guardar(comanda: Comanda) : Long

    @Update
     fun actualizar(comanda: Comanda)

    @Delete
     fun eliminar(comanda: Comanda)
}