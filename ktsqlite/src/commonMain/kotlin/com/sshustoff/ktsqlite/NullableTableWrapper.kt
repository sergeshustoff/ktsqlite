package com.sshustoff.ktsqlite

import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable

class NullableTableWrapper<T : CanConvertToSelectable>(
    private val table: T
) : CanConvertToSelectable {

    fun <R: ColumnType> nullOr(
        column: T.() -> Column<R, *>
    ): Column<R, ColumnNullability.NULL> {
        return column(table).let {
            Column(
                tableName = it.tableName,
                source = this,
                type = it.type,
                nullability = ColumnNullability.NULL,
                wrappedColumn = it,
                name = it.name
            )
        }
    }

    override fun convertToSelectable() = table.convertToSelectable()
}

fun <T : CanConvertToSelectable> T.asNullable() = NullableTableWrapper(this)