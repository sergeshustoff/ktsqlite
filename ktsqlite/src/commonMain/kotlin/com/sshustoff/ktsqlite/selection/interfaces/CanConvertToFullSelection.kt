package com.sshustoff.ktsqlite.selection.interfaces

import com.sshustoff.ktsqlite.QueryAlias
import com.sshustoff.ktsqlite.selection.FullSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

interface CanConvertToFullSelection<TablesRepresentation : Tables> {

    fun convertToFullSelection(): FullSelection<TablesRepresentation>

    fun alias(name: String) = QueryAlias(convertToFullSelection(), name)
}