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
}

object Converters {
  given modToValue[C <: Singleton, T](using
      v: ValueOf[C]
  ): Conversion[C => T, T] with {
    def apply(f: C => T): T = f(v.value)
  }
}

import Implicits.{variantToVariantSelector, iconNameToIconNameSelector}
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

case class Button(variant: Variant, size: Size, icon: IconName)

object Button {
  transparent inline def apply(
      inline variant: VariantSelector = _.Primary,
      inline size: SizeSelector = _.Medium,
      inline icon: IconNameSelector = _.Empty
  ): Button = {
    Button(
      variant = variant(Variant),
      size = size(Size),
      icon = icon(IconName)
    )
  }
}

@main def run() = {
  val button4 = Button(variant = Variant.Secondary, icon = _.Home)
  println(button4)
}
