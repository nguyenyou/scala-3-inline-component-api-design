package main

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
  // Usage - compiled to Button(Style.Button) with no lambda!
  val button1 = Button()
  val button2 = Button(variant = _.Secondary)
  val button3 = Button(variant = _.Secondary, size = _.Large)
  println(button1)
  println(button2)
  println(button3)
}
