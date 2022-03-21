package com.sshustoff.ktsqlite.selection

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.Order
import com.sshustoff.ktsqlite.internal.Join
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.selection.interfaces.CanConvertToFullSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

class FullSelection<TablesRepresentation : Tables> internal constructor(
    internal val tables: TablesRepresentation,
    val distinct: Boolean,
    internal val where: Predicate?,
    internal val orderBy: List<Order>,
    internal val groupBy: List<Column<*, *>>,
    internal val having: Predicate?,
    internal val originTable: Selectable<*>,
    internal val joins: List<Join>
) : CanConvertToFullSelection<TablesRepresentation> {

    override fun convertToFullSelection() = this
}