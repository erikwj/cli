package io.summarized.tools

import io.summarized.cli._
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

