package com.sshustoff.ktsqlite.selection.tuples

sealed class Tables {

    data class One<T>(val t1: T) : Tables()

    data class Two<T1, T2>(
        val t1: T1,
        val t2: T2
    ) : Tables()

    data class Three<T1, T2, T3>(
        val t1: T1,
        val t2: T2,
        val t3: T3
    ) : Tables()

    data class Four<T1, T2, T3, T4>(
        val t1: T1,
        val t2: T2,
        val t3: T3,
        val t4: T4
    ) : Tables()

    data class Five<T1, T2, T3, T4, T5>(
        val t1: T1,
        val t2: T2,
        val t3: T3,
        val t4: T4,
        val t5: T5
    ) : Tables()

    data class Six<T1, T2, T3, T4, T5, T6>(
        val t1: T1,
        val t2: T2,
        val t3: T3,
        val t4: T4,
        val t5: T5,
        val t6: T6
    ) : Tables()

    object Many : Tables()

    companion object {

        fun <T1, R> convertFun(
            source: (T1) -> R
        ) : (One<T1>) -> R {
            return { (t1) -> source.invoke(t1) }
        }

        fun <T1, T2, R> convertFun(
            source: (T1, T2) -> R
        ) : (Two<T1, T2>) -> R {
            return { (t1, t2) -> source.invoke(t1, t2) }
        }

        fun <T1, T2, T3, R> convertFun(
            source: (T1, T2, T3) -> R
        ) : (Three<T1, T2, T3>) -> R {
            return { (t1, t2, t3) -> source.invoke(t1, t2, t3) }
        }

        fun <T1, T2, T3, T4, R> convertFun(
            source: (T1, T2, T3, T4) -> R
        ) : (Four<T1, T2, T3, T4>) -> R {
            return { (t1, t2, t3, t4) -> source.invoke(t1, t2, t3, t4) }
        }

        fun <T1, T2, T3, T4, T5, R> convertFun(
            source: (T1, T2, T3, T4, T5) -> R
        ) : (Five<T1, T2, T3, T4, T5>) -> R {
            return { (t1, t2, t3, t4, t5) -> source.invoke(t1, t2, t3, t4, t5) }
        }

        fun <T1, T2, T3, T4, T5, T6, R> convertFun(
            source: (T1, T2, T3, T4, T5, T6) -> R
        ) : (Six<T1, T2, T3, T4, T5, T6>) -> R {
            return { (t1, t2, t3, t4, t5, t6) -> source.invoke(t1, t2, t3, t4, t5, t6) }
        }

        fun <R> convertFun(
            source: () -> R
        ) : (Many) -> R {
            return { _ -> source.invoke() }
        }

        fun <T1, R, S> convertFunReceiver(
            source: S.(T1) -> R
        ) : S.(One<T1>) -> R {
            return { (t1) -> source.invoke(this, t1) }
        }

        fun <T1, T2, R, S> convertFunReceiver(
            source: S.(T1, T2) -> R
        ) : S.(Two<T1, T2>) -> R {
            return { (t1, t2) -> source.invoke(this, t1, t2) }
        }

        fun <T1, T2, T3, R, S> convertFunReceiver(
            source: S.(T1, T2, T3) -> R
        ) : S.(Three<T1, T2, T3>) -> R {
            return { (t1, t2, t3) -> source.invoke(this, t1, t2, t3) }
        }

        fun <T1, T2, T3, T4, R, S> convertFunReceiver(
            source: S.(T1, T2, T3, T4) -> R
        ) : S.(Four<T1, T2, T3, T4>) -> R {
            return { (t1, t2, t3, t4) -> source.invoke(this, t1, t2, t3, t4) }
        }

        fun <T1, T2, T3, T4, T5, R, S> convertFunReceiver(
            source: S.(T1, T2, T3, T4, T5) -> R
        ) : S.(Five<T1, T2, T3, T4, T5>) -> R {
            return { (t1, t2, t3, t4, t5) -> source.invoke(this, t1, t2, t3, t4, t5) }
        }

        fun <T1, T2, T3, T4, T5, T6, R, S> convertFunReceiver(
            source: S.(T1, T2, T3, T4, T5, T6) -> R
        ) : S.(Six<T1, T2, T3, T4, T5, T6>) -> R {
            return { (t1, t2, t3, t4, t5, t6) -> source.invoke(this, t1, t2, t3, t4, t5, t6) }
        }

        fun <R, S> convertFunReceiver(
            source: S.() -> R
        ) : S.(Many) -> R {
            return { _ -> source.invoke(this) }
        }
    }
}