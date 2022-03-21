package com.sshustoff.ktsqlite.selection.interfaces

import com.sshustoff.ktsqlite.selection.FullSelection
import com.sshustoff.ktsqlite.selection.OrderableSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

interface CanConvertToOrderableSelection<TablesRepresentation : Tables> :
    CanConvertToFullSelection<TablesRepresentation> {

    fun convertToOrderableSelection(): OrderableSelection<TablesRepresentation>

    override fun convertToFullSelection(): FullSelection<TablesRepresentation> {
        return convertToOrderableSelection().orderByInternal { emptyList() }
    }
}