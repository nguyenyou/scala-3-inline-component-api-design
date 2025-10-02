package main
import scala.language.implicitConversions

object Implicits {
  inline implicit def variantToVariantSelector(
      v: Variant
  ): VariantSelector =
    _ => v
}

import Implicits.variantToVariantSelector

enum Variant {
  case Primary, Secondary
}

enum Size {
  case Small, Medium, Large
}

type VariantSelector = Variant.type => Variant
type SizeSelector = Size.type => Size

case class Button(variant: Variant, size: Size)

inline def defaultVariant: VariantSelector = _.Primary
inline def defaultSize: SizeSelector = _.Medium

object Button {
  transparent inline def apply(
      inline variant: VariantSelector = defaultVariant,
      inline size: SizeSelector = defaultSize
  ): Button = {
    Button(variant = variant(Variant), size = size(Size))
  }
}

@main def run() = {
  val button3 = Button(variant = Variant.Secondary)
  println(s"Regular usage (has default lambda): $button3")
}
