package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate

class NotEqPredicate(left: PieceOfPredicate, right: PieceOfPredicate) :
    OperatorPredicate(left, right, "!=")

infix fun CanConvertToPieceOfPredicate.notEq(right: CanConvertToPieceOfPredicate) =
    NotEqPredicate(this.convertToPieceOfPredicate(), right.convertToPieceOfPredicate())

infix fun CanConvertToPieceOfPredicate.notEq(right: Number) =
    NotEqPredicate(this.convertToPieceOfPredicate(), InLineNumber(right))

infix fun CanConvertToPieceOfPredicate.notEq(right: String) =
    NotEqPredicate(this.convertToPieceOfPredicate(), InLineString(right))