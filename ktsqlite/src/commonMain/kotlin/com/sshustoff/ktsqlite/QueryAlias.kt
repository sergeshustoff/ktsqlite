package com.sshustoff.ktsqlite

import com.sshustoff.ktsqlite.selection.FullSelection
import com.sshustoff.ktsqlite.selection.Selectable
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable
import com.sshustoff.ktsqlite.selection.tuples.Tables

data class QueryAlias<T: Tables>(
    val query: FullSelection<T>,
    val alias: String
) : CanConvertToSelectable {

    operator fun <R : ColumnType, N: ColumnNullability> invoke(
        column: (T) -> Column<R, N>
    ): Column<R, N> {
        return column(query.tables).let {
            Column(
                tableName = alias,
                source = this,
                type = it.type,
                nullability = it.nullability,
                wrappedColumn = it,
                name = it.alias
            )
        }
    }

    override fun convertToSelectable() = Selectable.FromQuery(this)
}