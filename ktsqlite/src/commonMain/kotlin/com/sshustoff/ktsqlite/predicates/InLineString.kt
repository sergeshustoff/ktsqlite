package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

class InLineString(val value: String) : PieceOfPredicate {
    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        statementBuilder.argument(value)
    }
}