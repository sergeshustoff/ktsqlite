package com.sshustoff.ktsqlite.selection.interfaces

import com.sshustoff.ktsqlite.selection.GroupedSelection
import com.sshustoff.ktsqlite.selection.OrderableSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

interface CanConvertToHavingableSelection<TablesRepresentation : Tables> :
    CanConvertToOrderableSelection<TablesRepresentation> {

    fun convertToHavingableSelection() : GroupedSelection<TablesRepresentation>

    override fun convertToOrderableSelection(): OrderableSelection<TablesRepresentation> {
        return convertToHavingableSelection().havingInternal { null }
    }
}