package io.summarized.tools

import java.io.{ByteArrayOutputStream, File}
import scala.sys.process._
import org.scalatest.Matchers
import org.scalatest.WordSpec
import io.summarized.cli._
import scalaz._
import Scalaz._

class ImageConverterSpec extends WordSpec with Matchers {

  "A ImageConverter" should {

    Executable.validate("convert") match {
      case Success(exe) => {
        "generate images from a PDF file" in {

          val sourceFile = new File("./resources/Notes.pdf")
          val outFile = new File("./resources/Notes.jpg")
          
          val config = new ImageConverterConfig {
            density := 200
          }
          val ic = ImageConverter(exe,config)

          val returnc = ic.run(sourceFile, outFile)

          returnc should equal (0)
        }

        "generate images from a PDF file a second time with outputStream" in {

          val sourceFile = new File("./resources/Notes.pdf")
          val outputStream = new ByteArrayOutputStream
    
          val ic = ImageConverter(exe,ImageConverterConfig.default)

          val returnc = ic.run(sourceFile, outputStream)
 
          outputStream.close()

          returnc should equal (0)
        }

      }
      case Failure(failure) =>
        "Skipping test, missing convert binary" in { true should equal(true) }
    }

    Executable.validate("no-convert") match {
      case Failure(fmsg) => fmsg should equal (NonEmptyList("no executable found for no-convert") )
      case Success(exe) => fail
    }


  }

}