package com.faqtfinding.tools

import com.faqtfinding.cli._
import scala.sys.process._
import com.faqtfinding.cli.ParamShow._
import ParamShowPdf._


object ParamShowPdf {
  
  implicit object PageOrientationParamShow extends ParamShow[PageOrientation] {
    override def show(prefix:String, name: String, value: PageOrientation): Iterable[String] =
      formatParam(prefix,name, Some(value.value))
  }

}

/**
 * Holds the configuration parameters of Pdf Kit
 */
trait PdfConfig extends CliConfig {

  /**
   * Options for `wkhtmltopdf` command
   * See `wkhtmltopdf --extended-help` for a description of each option
   */

  val defaultHeader = Parameter[Boolean]("default-header")

  val disableExternalLinks = Parameter[Boolean]("disable-external-links")

  val disableInternalLinks = Parameter[Boolean]("disable-internal-links")

  val disableJavascript = Parameter[Boolean]("disable-javascript")

  val disablePdfCompression = Parameter[Boolean]("disable-pdf-compression")

  val disableSmartShrinking = Parameter[Boolean]("disable-smart-shrinking")

  val convertForms = Parameter[Boolean]("forms")

  val encoding = Parameter[String]("encoding", "UTF-8")

  val grayScale = Parameter[Boolean]("grayscale")

  val lowQuality = Parameter[Boolean]("lowquality")

  val marginBottom = Parameter[String]("margin-bottom")

  val marginLeft = Parameter[String]("margin-left")

  val marginRight = Parameter[String]("margin-right")

  val marginTop = Parameter[String]("margin-top")

  val minimumFontSize = Parameter[Int]("minimum-font-size")

  val background = Parameter[Option[Boolean]]("background")

  val orientation = Parameter[PageOrientation]("orientation")

  val pageHeight = Parameter[String]("page-height")

  val pageOffset = Parameter[String]("page-offset")

  val pageSize = Parameter[String]("page-size")

  val pageWidth = Parameter[String]("page-width")

  val title = Parameter[String]("title")

  val tableOfContent = Parameter[Boolean]("toc")

  val zoom = Parameter[Float]("zoom")

  val footerCenter = Parameter[String]("footer-center")

  val footerFontName = Parameter[String]("footer-font-name")

  val footerFontSize = Parameter[String]("footer-font-size")

  val footerHtml = Parameter[String]("footer-html")

  val footerLeft = Parameter[String]("footer-left")

  val footerLine = Parameter[Boolean]("footer-line")

  val footerRight = Parameter[String]("footer-right")

  val footerSpacing = Parameter[Float]("footer-spacing")

  val headerCenter = Parameter[String]("header-center")

  val headerFontName = Parameter[String]("header-font-name")

  val headerFontSize = Parameter[String]("header-font-size")

  val headerHtml = Parameter[String]("header-html")

  val headerLeft = Parameter[String]("header-left")

  val headerLine = Parameter[Option[Boolean]]("header-line")

  val headerRight = Parameter[String]("header-right")

  val headerSpacing = Parameter[Float]("header-spacing")

  val tableOfContentDepth = Parameter[Int]("toc-depth")

  val tableOfContentDisableBackLinks = Parameter[Boolean]("toc-disable-back-links")

  val tableOfContentDisableLinks = Parameter[Boolean]("toc-disable-links")

  val tableOfContentFontName = Parameter[String]("toc-font-name")

  val tableOfContentHeaderFontName = Parameter[String]("toc-header-font-name")

  val tableOfContentHeaderFontSize = Parameter[Int]("toc-header-font-size")

  val tableOfContentHeaderText = Parameter[String]("toc-header-text")

  val tableOfContentLevel1FontSize = Parameter[Int]("toc-l1-font-size")

  val tableOfContentLevel1Indentation = Parameter[Int]("toc-l1-indentation")

  val tableOfContentLevel2FontSize = Parameter[Int]("toc-l2-font-size")

  val tableOfContentLevel2Indentation = Parameter[Int]("toc-l2-indentation")

  val tableOfContentLevel3FontSize = Parameter[Int]("toc-l3-font-size")

  val tableOfContentLevel3Indentation = Parameter[Int]("toc-l3-indentation")

  val tableOfContentLevel4FontSize = Parameter[Int]("toc-l4-font-size")

  val tableOfContentLevel4Indentation = Parameter[Int]("toc-l4-indentation")

  val tableOfContentLevel5FontSize = Parameter[Int]("toc-l5-font-size")

  val tableOfContentLevel5Indentation = Parameter[Int]("toc-l5-indentation")

  val tableOfContentLevel6FontSize = Parameter[Int]("toc-l6-font-size")

  val tableOfContentLevel6Indentation = Parameter[Int]("toc-l6-indentation")

  val tableOfContentLevel7FontSize = Parameter[Int]("toc-l7-font-size")

  val tableOfContentLevel7Indentation = Parameter[Int]("toc-l7-indentation")

  val tableOfContentNoDots = Parameter[Boolean]("toc-no-dots")

  val outline = Parameter[Option[Boolean]]("outline")

  val outlineDepth = Parameter[Int]("outline-depth")
  
  val username = Parameter[String]("username")
  
  val password = Parameter[String]("password")

  val printMediaType = Parameter[Option[Boolean]]("print-media-type")
  
}

object PdfConfig {

  /**
   * An instance of the default configuration
   */
  object default extends PdfConfig

  /**
   * Generates a sequence of command line parameters from a `PdfKitConfig`
   */
  def toParameters(config: PdfConfig): Seq[String] = {
    val prefix = "--"
    import config._
    Seq(
      convertForms.toParameter(prefix),
      defaultHeader.toParameter(prefix),
      disableExternalLinks.toParameter(prefix),
      disableInternalLinks.toParameter(prefix),
      disableJavascript.toParameter(prefix),
      disablePdfCompression.toParameter(prefix),
      disableSmartShrinking.toParameter(prefix),
      encoding.toParameter(prefix),
      footerCenter.toParameter(prefix),
      footerFontName.toParameter(prefix),
      footerFontSize.toParameter(prefix),
      footerHtml.toParameter(prefix),
      footerLeft.toParameter(prefix),
      footerLine.toParameter(prefix),
      footerRight.toParameter(prefix),
      footerSpacing.toParameter(prefix),
      grayScale.toParameter(prefix),
      headerCenter.toParameter(prefix),
      headerFontName.toParameter(prefix),
      headerFontSize.toParameter(prefix),
      headerHtml.toParameter(prefix),
      headerLeft.toParameter(prefix),
      headerLine.toParameter(prefix),
      headerRight.toParameter(prefix),
      headerSpacing.toParameter(prefix),
      lowQuality.toParameter(prefix),
      marginBottom.toParameter(prefix),
      marginLeft.toParameter(prefix),
      marginRight.toParameter(prefix),
      marginTop.toParameter(prefix),
      minimumFontSize.toParameter(prefix),
      background.toParameter(prefix),
      orientation.toParameter(prefix),
      outline.toParameter(prefix),
      outlineDepth.toParameter(prefix),
      pageHeight.toParameter(prefix),
      pageOffset.toParameter(prefix),
      pageSize.toParameter(prefix),
      pageWidth.toParameter(prefix),
      tableOfContent.toParameter(prefix),
      tableOfContentDepth.toParameter(prefix),
      tableOfContentDisableBackLinks.toParameter(prefix),
      tableOfContentDisableLinks.toParameter(prefix),
      tableOfContentFontName.toParameter(prefix),
      tableOfContentHeaderFontName.toParameter(prefix),
      tableOfContentHeaderFontSize.toParameter(prefix),
      tableOfContentHeaderText.toParameter(prefix),
      tableOfContentLevel1FontSize.toParameter(prefix),
      tableOfContentLevel1Indentation.toParameter(prefix),
      tableOfContentLevel2FontSize.toParameter(prefix),
      tableOfContentLevel2Indentation.toParameter(prefix),
      tableOfContentLevel3FontSize.toParameter(prefix),
      tableOfContentLevel3Indentation.toParameter(prefix),
      tableOfContentLevel4FontSize.toParameter(prefix),
      tableOfContentLevel4Indentation.toParameter(prefix),
      tableOfContentLevel5FontSize.toParameter(prefix),
      tableOfContentLevel5Indentation.toParameter(prefix),
      tableOfContentLevel6FontSize.toParameter(prefix),
      tableOfContentLevel6Indentation.toParameter(prefix),
      tableOfContentLevel7FontSize.toParameter(prefix),
      tableOfContentLevel7Indentation.toParameter(prefix),
      tableOfContentNoDots.toParameter(prefix),
      title.toParameter(prefix),
      zoom.toParameter(prefix),
      username.toParameter(prefix),
      password.toParameter(prefix),
      printMediaType.toParameter(prefix)
    ).flatten
  }

}
