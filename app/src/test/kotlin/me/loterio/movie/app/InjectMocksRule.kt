package me.loterio.movie.app

import io.mockk.MockKAnnotations
import org.junit.rules.TestRule

object InjectMocksRule {
    fun create(testClass: Any) = TestRule { statement, _ ->
        MockKAnnotations.init(testClass, relaxUnitFun = true)
        statement
    }
}
