package main
import scala.language.implicitConversions

object Implicits {
  inline implicit def variantToVariantSelector(
      v: Variant
  ): VariantSelector =
    _ => v

  inline implicit def iconNameToIconNameSelector(
      v: IconName
  ): IconNameSelector =
    _ => v

  inline implicit def iconSignalToIconSelectorSignal(
      s: Signal[IconName]
  ): Signal[IconNameSelector] =
    s.map(icon => _ => icon)
}

object Converters {
  given modToValue[C <: Singleton, T](using
      v: ValueOf[C]
  ): Conversion[C => T, T] with {
    def apply(f: C => T): T = f(v.value)
  }
}

trait Signal[+A] {
  def now(): A
  def map[B](project: A => B): Signal[B]
}

class Val[A](constantValue: A) extends Signal[A] {
  def now(): A = constantValue
  def map[B](project: A => B): Signal[B] = new Val(project(constantValue))
}

import Implicits.{
  variantToVariantSelector,
  iconNameToIconNameSelector,
  iconSignalToIconSelectorSignal
}
// import Converters.modToValue

enum Variant {
  case Primary, Secondary
}

enum Size {
  case Small, Medium, Large
}

enum IconName {
  case Home, Search, Settings, Empty
}

type VariantSelector = Variant.type => Variant

extension (value: Variant) {
  inline def selector: VariantSelector = _ => value
}

extension (selector: VariantSelector) {
  inline def value: Variant = selector(Variant)
}

type SizeSelector = Size.type => Size
type IconNameSelector = IconName.type => IconName

case class Button(variant: Variant, size: Size, icon: Signal[IconName])

object Button {
  inline def apply(
      inline variant: VariantSelector = _.Primary,
      inline size: SizeSelector = _.Medium,
      inline icon: Signal[IconNameSelector] = Val(_.Empty)
  ): Button = {
    Button(
      variant = variant(Variant),
      size = size(Size),
      icon = icon.map(_(IconName))
    )
  }
}

@main def run() = {
  val iconSignal: Signal[IconName] = Val(IconName.Home)

  val button4 = Button(variant = Variant.Secondary, icon = iconSignal)
  println(button4)
}
