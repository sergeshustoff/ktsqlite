package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.Predicate

class AndPredicate(left: Predicate, right: Predicate) : OperatorPredicate(left, right, "AND")

infix fun Predicate.and(right: Predicate) = AndPredicate(this, right)