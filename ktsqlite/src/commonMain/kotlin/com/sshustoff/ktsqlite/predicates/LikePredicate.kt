package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.Predicate

class LikePredicate(left: PieceOfPredicate, right: InLineString) :
    OperatorPredicate(left, right, "LIKE")

infix fun CanConvertToPieceOfPredicate.like(right: String): Predicate {
    return LikePredicate(this.convertToPieceOfPredicate(), InLineString(right))
}