package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.Predicate

class EqPredicate(left: PieceOfPredicate, right: PieceOfPredicate) :
    OperatorPredicate(left, right, "=")

infix fun CanConvertToPieceOfPredicate.eq(right: CanConvertToPieceOfPredicate) =
    EqPredicate(this.convertToPieceOfPredicate(), right.convertToPieceOfPredicate())

infix fun CanConvertToPieceOfPredicate.eq(right: Number?): Predicate {
    return if (right == null) {
        IsNullPredicate(this.convertToPieceOfPredicate())
    } else {
        EqPredicate(this.convertToPieceOfPredicate(), InLineNumber(right))
    }
}

infix fun CanConvertToPieceOfPredicate.eq(right: String?): Predicate {
    return if (right == null) {
        IsNullPredicate(this.convertToPieceOfPredicate())
    } else {
        EqPredicate(this.convertToPieceOfPredicate(), InLineString(right))
    }
}