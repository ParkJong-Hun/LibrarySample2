package co.kr.parkjonghun.librarysample

import Foo
import org.junit.Test

import org.junit.Assert.*

class FooTest {
    @Test
    fun `test_foo`() {
        val actual = Foo().hoge(2.0)
        assertEquals(6.28, actual)
    }
}