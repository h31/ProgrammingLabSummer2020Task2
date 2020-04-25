package ru.nikiens.packrle.io

import spock.lang.Specification
import spock.lang.Unroll

class DecodedInputStreamTest extends Specification {
    @Unroll
    def "Test invalid read() args: #reason"() {
        given:
            def arr = new byte[5]
            def dis = new DecodedInputStream()
        when:
            dis.read(arr, off, len)
        then:
            thrown(IndexOutOfBoundsException)
        where:
            off | len || reason
            -2  | 0   || 'off < 0'
            6   | 0   || 'off > array length'
            0   | -3  || 'len < 0'
            6   | 6   || 'off + len > array length'
            -1  | -1  || 'off + len < 0'
    }

    def "Test decoding"() {
        given:
            def dis = new DecodedInputStream(new ByteArrayInputStream(input as byte[]))
        expect:
            dis.withReader {
                it.readLine() == expected
            }
        where:
            input        || expected
            [97]         || 'a'
            [97, 97, 0]  || 'aa'
            [97, 98, 99] || 'abc'
            [97, 97, 3]  || 'a' * 5
            [0, 0, 0]    || '\u0000' * 2
    }

    def "Test decoding large runs"() {
        given:
            def dis = new DecodedInputStream(new ByteArrayInputStream(input as byte[]))
        expect:
            dis.withReader {
                it.readLine() == expected
            }
        where:
            input                    || expected
            [97, 97, -1]             || 'a' * 257
            [97, 97, -1, 97]         || 'a' * 258
            [97, 97, -1, 97, 97, 0]  || 'a' * 259
            [97, 97, -1, 97, 97, 1]  || 'a' * 260
            [97, 97, -1, 97, 97, -1] || 'a' * 257 * 2
    }
}
