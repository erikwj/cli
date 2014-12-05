package com.faqtfinding.spdf

import com.faqtfinding.cli._
import ParamShow._

sealed trait PageOrientation {
  val value: String
}

object Landscape extends PageOrientation {
  override val value = "Landscape"
}

object Portrait extends PageOrientation {
  override val value = "Portrait"
}

object ParamShowPdf {

  implicit object PageOrientationParamShow extends ParamShow[PageOrientation] {
    override def show(name: String, value: PageOrientation): Iterable[String] =
      formatParam(name, Some(value.value))
  }

}