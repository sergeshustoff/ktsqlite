package com.sshustoff.ktsqlite.predicates

import com.sshustoff.ktsqlite.predicates.core.Predicate

class OrPredicate(left: Predicate, right: Predicate) : OperatorPredicate(left, right, "OR")

infix fun Predicate.or(right: Predicate): Predicate {
    return OrPredicate(this, right)
}