package com.sshustoff.ktsqlite.changes

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.ColumnNullability
import com.sshustoff.ktsqlite.ColumnType

class InsertContext {

    val inserts = ArrayList<Insert>()

    infix fun Column<ColumnType.Long, ColumnNullability.NOT_NULL>.set(value: Int) =
        addSafeValueInternal(this, value)

    infix fun Column<ColumnType.Long, ColumnNullability.NULL>.set(value: Int?) =
        addSafeValueInternal(this, value)

    infix fun Column<ColumnType.Long, ColumnNullability.NOT_NULL>.set(value: Long) =
        addSafeValueInternal(this, value)

    infix fun Column<ColumnType.Long, ColumnNullability.NULL>.set(value: Long?) =
        addSafeValueInternal(this, value)

    infix fun Column<ColumnType.Float, ColumnNullability.NOT_NULL>.set(value: Float) =
        addSafeValueInternal(this, value)

    infix fun Column<ColumnType.Float, ColumnNullability.NULL>.set(value: Float?) =
        addSafeValueInternal(this, value)

    infix fun Column<ColumnType.Text, ColumnNullability.NOT_NULL>.set(value: String) {
        inserts.add(Insert(this, value, putInArguments = true))
    }

    @JvmName("setNullable")
    infix fun Column<ColumnType.Text, ColumnNullability.NULL>.set(value: String?) {
        inserts.add(Insert(this, value ?: "NULL", putInArguments = value != null))
    }

    private fun addSafeValueInternal(column: Column<*, *>, value: Any?) {
        inserts.add(Insert(column, value?.toString() ?: "NULL", putInArguments = false))
    }

    data class Insert(
        val column: Column<*, *>,
        val value: String,
        val putInArguments: Boolean
    )
}