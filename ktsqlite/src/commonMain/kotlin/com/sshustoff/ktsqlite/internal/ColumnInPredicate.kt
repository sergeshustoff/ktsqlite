package com.sshustoff.ktsqlite.internal

import com.sshustoff.ktsqlite.Column
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

data class ColumnInPredicate(
    val column: Column<*, *>
) : PieceOfPredicate {

    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        statementBuilder.sql(column.fullName)
    }

    override fun findUsedColumns() = listOf(column)
}