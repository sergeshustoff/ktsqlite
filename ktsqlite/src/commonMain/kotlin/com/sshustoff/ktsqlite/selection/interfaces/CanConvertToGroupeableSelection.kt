package com.sshustoff.ktsqlite.selection.interfaces

import com.sshustoff.ktsqlite.selection.ConditionalSelection
import com.sshustoff.ktsqlite.selection.tuples.Tables

interface CanConvertToGroupeableSelection<TablesRepresentation: Tables> :
    CanConvertToHavingableSelection<TablesRepresentation> {

    fun convertToGroupeableSelection() : ConditionalSelection<TablesRepresentation>

    override fun convertToHavingableSelection() =
        convertToGroupeableSelection().groupByInternal { emptyList() }
}