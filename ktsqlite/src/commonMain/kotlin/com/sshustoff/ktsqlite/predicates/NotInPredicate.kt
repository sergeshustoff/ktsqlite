package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder

class NotInPredicate(
    val left: PieceOfPredicate,
    val right: Collection<PieceOfPredicate>
) : Predicate {

    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        statementBuilder.sql("(")
        left.addToStatement(statementBuilder)
        statementBuilder.sql(" NOT IN (")

        var isFirst = true
        for (pieceOfPredicate in right) {
            if (!isFirst) {
                statementBuilder.sql(",")
            } else {
                isFirst = false
            }
            pieceOfPredicate.addToStatement(statementBuilder)
        }

        statementBuilder.sql("))")
    }

    override fun findUsedColumns() = left.findUsedColumns()
}

infix fun CanConvertToPieceOfPredicate.notIn(collection: Collection<Number>): Predicate {
    return NotInPredicate(this.convertToPieceOfPredicate(), collection.map { InLineNumber(it) })
}

@JvmName("inStrings")
infix fun CanConvertToPieceOfPredicate.notIn(collection: Collection<String>): Predicate {
    return NotInPredicate(this.convertToPieceOfPredicate(), collection.map { InLineString(it) })
}