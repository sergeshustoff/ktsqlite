package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

class InLineNumber(val value: Number) : PieceOfPredicate {
    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        statementBuilder.sql(value.toString())
    }
}