package com.sshustoff.ktsqlite

import com.sshustoff.ktsqlite.selection.Selectable
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable

data class TableAlias<T : Table>(
    val table: T,
    val alias: String
) : CanConvertToSelectable {

    operator fun <R : ColumnType, N: ColumnNullability> invoke(
        column: T.() -> Column<R, N>
    ): Column<R, N> {
        return column(table).let {
            Column(
                tableName = alias,
                source = this,
                type = it.type,
                nullability = it.nullability,
                wrappedColumn = it,
                name = it.name
            )
        }
    }

    override fun convertToSelectable() = Selectable.FromAlias(this)
}

fun <T : Table> T.asAlias(alias: String) = TableAlias(this, alias)