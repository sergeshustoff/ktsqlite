package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

abstract class OperatorPredicate(
    val left: PieceOfPredicate,
    val right: PieceOfPredicate,
    val operator: String
) : Predicate {

    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        statementBuilder.sql("(")
        left.addToStatement(statementBuilder)
        statementBuilder.sql(" ")
        statementBuilder.sql(operator)
        statementBuilder.sql(" ")
        right.addToStatement(statementBuilder)
        statementBuilder.sql(")")
    }

    override fun findUsedColumns() = left.findUsedColumns() + right.findUsedColumns()
}