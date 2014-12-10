package io.summarized.tools

import org.scalatest.Matchers
import org.scalatest.WordSpec

class ImageConverterConfigSpec extends WordSpec with Matchers {

  "ImageConverterConfig" should {

    "have a default config" in {
      ImageConverterConfig.toParameters(ImageConverterConfig.default) should equal(Seq())
    }

    "generate parameters from config" in {
      val config = new ImageConverterConfig {
        density := 100
      }
      ImageConverterConfig.toParameters(config) should equal(Seq("-density", "100"))
    }

  }

}
