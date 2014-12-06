package com.faqtfinding.tools

import java.io.{ByteArrayOutputStream, File}
import scala.sys.process._
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import com.faqtfinding.cli._

class ImageConverterSpec extends WordSpec with ShouldMatchers {

  "A ImageConverter" should {

    // "require the executionPath config" in {
    //   val no_exe = Executable.validate("notexecutable")
    //   // val file = new File("notexecutable")
    //   // val filePath = file.getAbsolutePath

    //   // evaluating {
    //   //   no_exe map { (ne) => new ImageConverter(ne, ImageConverterConfig.default) }
    //   // } should be None
    //   todo
    //   // evaluating {
    //   //   ImageConverter(no_exe, ImageConverterConfig.default)
    //   // } should produce[NoExecutableException]

    // }

    Executable.validate("convert") match {
      case Some(exe) => {
        "generate images from a PDF file" in {

          val sourceFile = new File("/Users/erikjanssen/Copy/KvK/form.pdf")
          val outFile = new File("/Users/erikjanssen/Copy/KvK/formulier.jpg")
          
          val config = new ImageConverterConfig {
            density := 100
          }
          val ic = ImageConverter(exe,config).getOrElse(sys.error("no Executable available"))

          val returnc = ic.run(sourceFile, outFile)

          returnc should equal (0)
        }
        "generate images from a PDF file a second time with outputStream" in {

          val sourceFile = new File("/Users/erikjanssen/Copy/KvK/Aantekeningen.pdf")
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


  }

}