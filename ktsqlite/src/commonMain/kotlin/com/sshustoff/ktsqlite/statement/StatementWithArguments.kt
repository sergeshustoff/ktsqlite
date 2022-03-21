package com.sshustoff.ktsqlite.statement

data class StatementWithArguments(
    val sql: String,
    val args: Array<String>
)