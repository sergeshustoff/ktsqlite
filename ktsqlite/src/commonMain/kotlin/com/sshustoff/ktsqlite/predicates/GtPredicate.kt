package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.CanConvertToPieceOfPredicate
import com.sshustoff.ktsqlite.predicates.core.PieceOfPredicate

class GtPredicate(left: PieceOfPredicate, right: PieceOfPredicate) :
    OperatorPredicate(left, right, ">")

infix fun CanConvertToPieceOfPredicate.gt(right: CanConvertToPieceOfPredicate) =
    GtPredicate(this.convertToPieceOfPredicate(), right.convertToPieceOfPredicate())

infix fun CanConvertToPieceOfPredicate.gt(right: Number) =
    GtPredicate(this.convertToPieceOfPredicate(), InLineNumber(right))

infix fun CanConvertToPieceOfPredicate.gt(right: String) =
    GtPredicate(this.convertToPieceOfPredicate(), InLineString(right))