package com.sshustoff.ktsqlite.android

import android.database.Cursor
import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.parsing.SqlResultParser

class CursorParser(private val cursor: Cursor) : SqlResultParser {

    override fun getLong(column: Column<*, *>): Long? = takeNullable(column) { getLong(it) }

    override fun getFloat(column: Column<*, *>): Float? = takeNullable(column) { getFloat(it) }

    override fun getText(column: Column<*, *>): String? = takeNullable(column) { getString(it) }

    private fun <T> takeNullable(column: Column<*, *>, extract: Cursor.(Int) -> T) : T? {
        return if (!isNull(column)) {
            cursor.extract(getColumnId(column))
        } else {
            null
        }
    }

    private fun isNull(column: Column<*, *>) = cursor.isNull(getColumnId(column))

    private fun getColumnId(column: Column<*, *>) = cursor.getColumnIndexOrThrow(column.alias)
}