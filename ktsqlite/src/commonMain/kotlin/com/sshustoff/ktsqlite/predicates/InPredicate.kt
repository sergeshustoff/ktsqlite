package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.Predicate
import com.sshustoff.ktsqlite.statement.StatementWithArgumentsBuilder
import kotlin.jvm.JvmName

class InPredicate(
    val left: PieceOfPredicate,
    val right: Collection<PieceOfPredicate>
) : Predicate {

    override fun addToStatement(statementBuilder: StatementWithArgumentsBuilder) {
        statementBuilder.sql("(")
        left.addToStatement(statementBuilder)
        statementBuilder.sql(" IN (")

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

infix fun CanConvertToPieceOfPredicate.`in`(collection: Collection<Number>): Predicate {
    return InPredicate(this.convertToPieceOfPredicate(), collection.map { InLineNumber(it) })
}

@JvmName("inStrings")
infix fun CanConvertToPieceOfPredicate.`in`(collection: Collection<String>): Predicate {
    return InPredicate(this.convertToPieceOfPredicate(), collection.map { InLineString(it) })
}