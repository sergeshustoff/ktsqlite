package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate

class GteqPredicate(left: PieceOfPredicate, right: PieceOfPredicate) :
    OperatorPredicate(left, right, ">=")

infix fun CanConvertToPieceOfPredicate.gteq(right: CanConvertToPieceOfPredicate) =
    GteqPredicate(this.convertToPieceOfPredicate(), right.convertToPieceOfPredicate())

infix fun CanConvertToPieceOfPredicate.gteq(right: Number) =
    GteqPredicate(this.convertToPieceOfPredicate(), InLineNumber(right))

infix fun CanConvertToPieceOfPredicate.gteq(right: String) =
    GteqPredicate(this.convertToPieceOfPredicate(), InLineString(right))