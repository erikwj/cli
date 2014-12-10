package io.summarized.tools

import java.io.{ByteArrayOutputStream, File}
import scala.sys.process._
import org.scalatest.Matchers
import org.scalatest.WordSpec
import io.summarized.cli._
import scalaz._
import Scalaz._

class ExecutableSpec extends WordSpec with Matchers {

  "An Executable" should {
    
    "return an instance a default config" in {
    
      Executable.validate("cat") match {
        case Success(exe) => true should equal(true) // exe should be instance of Executable
        case Failure(failure) => fail
      }
    }

    "return an error message otherwise" in {
      Executable.validate("dog") match {
        case Failure(fmsg) => fmsg should equal (NonEmptyList("no executable found for dog") )
        case Success(exe) => fail
      }
    }

  }

}