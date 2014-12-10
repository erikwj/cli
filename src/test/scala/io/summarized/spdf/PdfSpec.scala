package io.summarized.tools

import java.io.File
import scala.sys.process._
import org.scalatest.Matchers
import org.scalatest.WordSpec
import io.summarized.cli._
import scalaz._
import Scalaz._

class PdfSpec extends WordSpec with Matchers {

  "A Pdf" should {

    Executable.validate("wkhtmltopdf") match {
      case Success(exe) =>
        "generate a PDF file from an HTML string" in {

          val page =
            """
              |<html><body><h1>Hello</h1></body></html>
            """.stripMargin

          val file = File.createTempFile("scala.spdf", "pdf")

          val pdf = Pdf(PdfConfig.default)

          pdf.run(page, file)

          Seq("file", file.getAbsolutePath).!! should include("PDF document")
        }

      case Failure(fmsg) => fail
    }


  }

}