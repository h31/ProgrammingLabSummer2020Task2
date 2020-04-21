package ru.nikiens.packrle.io

import spock.lang.Specification
import spock.lang.Unroll

class EncodedOutputStreamTest extends Specification {
    def baos = new ByteArrayOutputStream()
    def eos = new EncodedOutputStream(baos)

    @Unroll
    def "Test invalid eos.write() arguments: #reason"() {
        given:
            def arr = new byte[5]
        when:
            eos.withCloseable {
                it.write(arr, off, len)
            }
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

    def "Test encoding"() {
        when:
            eos.withWriter {
                it.write(input)
            }
        then:
            baos.toByteArray() == output as byte[]
        where:
            input    || output
            'a'      || [97]
            'aa'     || [97, 97, 0]
            'abc'    || [97, 98, 99]
            'a' * 5  || [97, 97, 3]
    }

    def "Test encoding large runs"() {
        when:
            eos.withWriter {
                it.write(input)
            }
        then:
            baos.toByteArray() == output as byte[]
        where:
            input         || output
            'a' * 257     || [97, 97, -1]
            'a' * 258     || [97, 97, -1, 97]
            'a' * 259     || [97, 97, -1, 97, 97, 0]
            'a' * 260     || [97, 97, -1, 97, 97, 1]
            'a' * 257 * 2 || [97, 97, -1, 97, 97, -1]
    }
}