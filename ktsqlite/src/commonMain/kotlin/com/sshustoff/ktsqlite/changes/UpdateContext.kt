package com.sshustoff.ktsqlite.changes

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.ColumnNullability
import com.sshustoff.ktsqlite.ColumnType
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder
import kotlin.jvm.JvmName

class UpdateContext(private val builder: StatementWithArgumentsBuilder) {

    private var isFirst = true

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

    infix fun Column<ColumnType.Text, ColumnNullability.NOT_NULL>.set(value: String) =
        addTextValueInternal(this, value)

    @JvmName("setNullable")
    infix fun Column<ColumnType.Text, ColumnNullability.NULL>.set(value: String?) {
        if (value == null) {
            addSafeValueInternal(this, value)
        } else {
            addTextValueInternal(this, value)
        }
    }

    private fun addTextValueInternal(column: Column<*, *>, value: String) {
        if (isFirst) {
            isFirst = false
        } else {
            builder.sql(", ")
        }
        builder.sql("${column.name} = ")
        builder.argument(value)
    }

    private fun addSafeValueInternal(column: Column<*, *>, value: Any?) {
        if (isFirst) {
            isFirst = false
        } else {
            builder.sql(", ")
        }
        builder.sql("${column.name} = ${value ?: "NULL"}")
    }
}