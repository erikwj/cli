package com.faqtfinding.tools

import java.io.File
import scala.sys.process._
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.WordSpec
import com.faqtfinding.cli._

class PdfSpec extends WordSpec with ShouldMatchers {

  "A Pdf" should {

    Executable.validate("wkhtmltopdf") match {
      case Some(exe) =>
        "generate a PDF file from an HTML string" in {

          val page =
            """
              |<html><body><h1>Hello</h1></body></html>
            """.stripMargin

          val file = File.createTempFile("scala.spdf", "pdf")

          val pdf = Pdf(PdfConfig.default).getOrElse(sys.error("no Executable available"))

          pdf.run(page, file)

          Seq("file", file.getAbsolutePath).!! should include("PDF document")
        }

      case None =>
        "Skipping test, missing wkhtmltopdf binary" in { true should equal(true) }
    }


  }

}