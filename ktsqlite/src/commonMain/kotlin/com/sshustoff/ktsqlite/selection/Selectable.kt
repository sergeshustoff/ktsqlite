package com.sshustoff.ktsqlite.selection

import com.sshustoff.ktsqlite.QueryAlias
import com.sshustoff.ktsqlite.Table
import com.sshustoff.ktsqlite.TableAlias
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToSelectable

sealed class Selectable<T>(val source: T) : CanConvertToSelectable {
    class FromTable(source: Table) : Selectable<Table>(source)
    class FromAlias(source: TableAlias<*>) : Selectable<TableAlias<*>>(source)
    class FromQuery(source: QueryAlias<*>) : Selectable<QueryAlias<*>>(source)

    override fun convertToSelectable() = this
}