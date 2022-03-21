package com.sshustoff.ktsqlite.predicates.core

interface CanConvertToPieceOfPredicate {

    fun convertToPieceOfPredicate(): PieceOfPredicate
}