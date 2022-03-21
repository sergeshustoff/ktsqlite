package com.sshustoff.ktsqlite.statement

import com.sshustoff.ktsqlite.parsing.SqlResultParser

interface StatementExecutor {
    fun <R> select(sql: String, args: Array<String>, itemParser: (SqlResultParser) -> R): List<R>
    fun insert(sql: String, args: Array<String>): Long
    fun execute(sql: String, args: Array<String>)
    suspend fun <T> inTransaction(block: () -> T): T
    fun <T> inTransactionBlocking(block: () -> T): T
}
