package com.faqtfinding.tools

import java.io.{ByteArrayOutputStream, File}
import scala.sys.process._
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import com.faqtfinding.cli._

class ImageConverterSpec extends WordSpec with ShouldMatchers {

  "A ImageConverter" should {

    Executable.validate("convert") match {
      case Some(exe) => {
        "generate images from a PDF file" in {

          val sourceFile = new File("./resources/Notes.pdf")
          val outFile = new File("./resources/Notes.jpg")
          
          val config = new ImageConverterConfig {
            density := 200
          }
          val ic = ImageConverter(exe,config).getOrElse(sys.error("no Executable available"))

          val returnc = ic.run(sourceFile, outFile)

          returnc should equal (0)
        }

        "generate images from a PDF file a second time with outputStream" in {

          val sourceFile = new File("./resources/Notes.pdf")
          val outputStream = new ByteArrayOutputStream
    
          val ic = ImageConverter(exe,ImageConverterConfig.default).getOrElse(sys.error("no Executable available"))

          val returnc = ic.run(sourceFile, outputStream)
 
          outputStream.close()

          returnc should equal (0)
        }

      }
      case None =>
        "Skipping test, missing convert binary" in { true should equal(true) }
    }

    Executable.validate("no-convert") match {
      case Some(exe) => { //shouldn't be here
        
      }
      case None =>
        "Skipping test, missing convert binary" in { true should equal(true) }
    }


  }

}