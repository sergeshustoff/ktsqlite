package com.sshustoff.ktsqlite.internal

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.parsing.SqlResultParser

internal class SelectionColumnsExtractor : SqlResultParser {

    val columns = ArrayList<Column<*, *>>()

    override fun getLong(column: Column<*, *>): Long? {
        columns.add(column)
        return 0
    }

    override fun getFloat(column: Column<*, *>): Float? {
        columns.add(column)
        return 0F
    }

    override fun getText(column: Column<*, *>): String? {
        columns.add(column)
        return ""
    }
}