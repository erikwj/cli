package com.faqtfinding.cli

/**
 * Describes a command line option
 */
trait Parameter[T] {
  /**
   * The underlying value of this option
   */
  private var underlying: Option[T] = None

  /**
   * The commandline name for this parameter
   */
  val name: String

  /**
   * The optional default value for this parameter
   */
  val default: Option[T] = None

  /**
   * Sets a new value for this parameter
   */
  def :=(newValue: T): Unit = underlying = Some(newValue)

  /**
   * Converts this option to a sequence of strings to be appended to the
   * command line
   */
  def toParameter(implicit shower: ParamShow[T]): Iterable[String] = value match {
    case Some(v) => shower.show(name, v)
    case _ => Iterable.empty
  }

  /**
   * Provides the current value for the option
   */
  private def value: Option[T] = underlying orElse default

}

object Parameter {

  /**
   * Creates a new CommandOption with the specified name and default value
   */
  def apply[T : ParamShow](commandName: String, defaultValue: T): Parameter[T] =
    new Parameter[T] {
      override val name: String = commandName
      override val default: Option[T] = Some(defaultValue)
    }

  /**
   * Creates a new CommandOption with the specified name
   */
  def apply[T : ParamShow](commandName: String): Parameter[T] =
    new Parameter[T] {
      override val name: String = commandName
      override val default: Option[T] = None
    }

}

/**
 * Renders a parameter of type T to a sequence of strings that will be
 * appended to the command line.
 */
trait ParamShow[T] {
  def show(name: String, value: T): Iterable[String]
}

object ParamShow {

  implicit object StringParamShow extends ParamShow[String] {
    override def show(name: String, value: String): Iterable[String] =
      formatParam(name, Some(value))
  }

  implicit object BooleanParamShow extends ParamShow[Boolean] {
    override def show(name: String, value: Boolean): Iterable[String] = value match {
      case true => formatParam(name, None)
      case _ => Iterable.empty
    }
  }

  implicit object OptionBooleanParamShow extends ParamShow[Option[Boolean]] {
    override def show(name: String, valueOpt: Option[Boolean]): Iterable[String] =
      valueOpt.toIterable.flatMap { formatParam(name, _) }
  }

  implicit object IntParamShow extends ParamShow[Int] {
    override def show(name: String, value: Int): Iterable[String] =
      formatParam(name, Some(value.toString))
  }

  implicit object FloatParamShow extends ParamShow[Float] {
    override def show(name: String, value: Float): Iterable[String] =
      formatParam(name, Some("%.2f".format(value)))
  }

  def formatParam(name: String, value: Option[String]): Iterable[String] =
    Seq(Some("--" + name), value).flatten

  def formatParam(name: String, value: Boolean): Iterable[String] = if(value) {
    Some("--" + name)
  } else {
    Some("--no-" + name)
  }

}

