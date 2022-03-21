package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

class IsNotNullPredicate(val value: PieceOfPredicate) : Predicate {

    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        with(statementBuilder) {
            sql("(")
            value.addToStatement(this)
            sql(" IS NOT NULL)")
        }
    }
}

fun CanConvertToPieceOfPredicate.isNotNull() =
    IsNotNullPredicate(this.convertToPieceOfPredicate())