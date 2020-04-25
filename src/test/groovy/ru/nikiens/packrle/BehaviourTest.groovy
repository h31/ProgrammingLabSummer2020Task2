package ru.nikiens.packrle

import picocli.CommandLine

import ru.nikiens.packrle.util.ExtensionUtil

import spock.lang.Specification

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

class BehaviourTest extends Specification {
    def encoded = Paths.get("src", "test", "resources", "encoded.txt.rlz")
    def decoded = Paths.get("src", "test", "resources", "decoded.txt")
    def baos = new ByteArrayOutputStream()
    def cmd = new CommandLine(new PackRLE())
    def eu = new ExtensionUtil("rlz")

    static def getFileContents(Path path) {
        Files.readAllLines(path).join(System.lineSeparator())
    }

    def "stdin -> stdout by default"() {
        setup: "Simulate standard input/output"
            System.setIn(Files.newInputStream(decoded))
            System.setOut(new PrintStream(baos))
        when:
            cmd.execute()
        then:
            baos.toString() == getFileContents(encoded)
    }

    def "Must decompress by default for .rlz extension"() {
        given:
            def derlz = eu.truncate(encoded)
        when:
            cmd.execute(encoded.toString())
        then:
            getFileContents(decoded) == getFileContents(derlz)
        cleanup:
            Files.delete(derlz)
    }

    def "Decompress to stdout, if forced and no <output> specified"() {
        setup: "Simulate standard output"
            System.setOut(new PrintStream(baos))
        and: "Create derlz-ed file"
            def derlz = Files.copy(encoded, eu.truncate(encoded))
        when:
            cmd.execute("-u", derlz.toString())
        then:
            baos.toString().split("\r\n").join(System.lineSeparator()) == getFileContents(decoded)
        cleanup:
            Files.delete(derlz)
    }

    def "Decompress to <output>, if specified"() {
        given:
            def output = Paths.get("test")
        when:
            cmd.execute(encoded.toString(), "-u", "-out", output.toString())
        then:
            getFileContents(output) == getFileContents(decoded)
        cleanup:
            Files.delete(output)
    }

    def "Must compress other files by default"() {
        given:
            def rlz = eu.append(decoded)
        when:
            cmd.execute(decoded.toString())
        then:
            getFileContents(rlz) == getFileContents(encoded)
        cleanup:
            Files.delete(rlz)
    }

    def "Compress to <output>, if specified"() {
        given:
            def output = Paths.get("test")
        when:
            cmd.execute(decoded.toString(), "-out", output.toString())
        then:
            getFileContents(output) == getFileContents(encoded)
        cleanup:
            Files.delete(output)
    }
}