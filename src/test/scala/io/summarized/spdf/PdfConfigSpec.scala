package io.summarized.tools

import org.scalatest.Matchers
import org.scalatest.WordSpec

class PdfConfigSpec extends WordSpec with Matchers {

  "PdfConfig" should {

    "have a default config" in {
      PdfConfig.toParameters(PdfConfig.default) should equal(Seq("--encoding", "UTF-8"))
    }

    "generate parameters from config" in {
      val config = new PdfConfig {
        convertForms := true
        marginBottom := "1in"
        minimumFontSize := 3
        orientation := Landscape
        zoom := 1.23f
      }
      PdfConfig.toParameters(config) should equal(Seq("--forms", "--encoding", "UTF-8", "--margin-bottom", "1in", "--minimum-font-size", "3", "--orientation", "Landscape", "--zoom", "%.2f".format(1.23f)))
    }

    "print media type" in {
      val config = new PdfConfig {
        printMediaType := Some(true)
      }
      PdfConfig.toParameters(config) should contain("--print-media-type")
    }

    "no print media type" in {
      val config = new PdfConfig {
        printMediaType := Some(false)
      }
      PdfConfig.toParameters(config) should contain("--no-print-media-type")
    }

  }

}
