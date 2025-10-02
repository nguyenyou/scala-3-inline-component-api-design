package main
import scala.language.implicitConversions

object Implicits {
  transparent inline implicit def variantToVariantSelector(
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

object Button {
  transparent inline def apply(
      inline variant: VariantSelector = _.Primary,
      inline size: SizeSelector = _.Medium
  ): Button = {
    Button(variant = variant(Variant), size = size(Size))
  }
}

@main def run() = {
  val button3 = Button(variant = Variant.Secondary)
  println(s"Regular usage (has default lambda): $button3")
}
