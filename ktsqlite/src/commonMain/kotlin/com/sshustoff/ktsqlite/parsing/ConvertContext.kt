package com.sshustoff.ktsqlite.parsing

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.ColumnNullability
import com.sshustoff.ktsqlite.ColumnType
import java.lang.IllegalStateException

class ConvertContext(private val parser: SqlResultParser) {

    val Column<ColumnType.Id, ColumnNullability.NOT_NULL>.read: Long
        @JvmName("readId")
        get() = parser.getLong(this) ?: throwPropertyNull()

    val Column<ColumnType.Id, ColumnNullability.NULL>.read: Long?
        @JvmName("readNullableId")
        get() = parser.getLong(this)

    val Column<ColumnType.Long, ColumnNullability.NOT_NULL>.read: Long
        get() = parser.getLong(this) ?: throwPropertyNull()

    val Column<ColumnType.Long, ColumnNullability.NULL>.read: Long?
        get() = parser.getLong(this)

    val Column<ColumnType.Float, ColumnNullability.NOT_NULL>.read: Float
        get() = parser.getFloat(this) ?: throwPropertyNull()

    val Column<ColumnType.Float, ColumnNullability.NULL>.read: Float?
        get() = parser.getFloat(this)

    val Column<ColumnType.Text, ColumnNullability.NOT_NULL>.read: String
        get() = parser.getText(this) ?: throwPropertyNull()

    val Column<ColumnType.Text, ColumnNullability.NULL>.read: String?
        @JvmName("readNullableText")
        get() = parser.getText(this)

    private fun Column<*, ColumnNullability.NOT_NULL>.throwPropertyNull(): Nothing {
        throw IllegalStateException("Error, $fullName is null")
    }
}